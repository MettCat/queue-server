package com.nettyserversql;

import com.nettyserversql.bigqueue.BigQueueImpl;
import com.nettyserversql.bigqueue.IBigQueue;
import com.nettyserversql.bigqueue.utils.TestUtil;
import com.nettyserversql.controller.bigController;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/8/9.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class bigQueue {


    private String testDir = TestUtil.TEST_BASE_DIR + "bigqueue/unit";
    private IBigQueue bigQueue2;
    {
        try {
            bigQueue2 = new BigQueueImpl(testDir, "simple_test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void bigTest1() throws IOException {
        bigQueue2 = new BigQueueImpl(testDir, "simple_test");
        for(int i = 1; i <= 2; i++) {

            assertNotNull(bigQueue2);//线程阻塞

            for(int j = 1; j <= 3; j++) {
                assertTrue(bigQueue2.size() == 0L);
                assertTrue(bigQueue2.isEmpty());

                assertNull(bigQueue2.dequeue());
                assertNull(bigQueue2.peek());


                bigQueue2.enqueue("hello".getBytes());//将该数据添加到队列后面
                assertTrue(bigQueue2.size() == 1L);
                assertTrue(!bigQueue2.isEmpty());//确定队列是否为空
                assertEquals("hello", new String(bigQueue2.peek()));
                assertEquals("hello", new String(bigQueue2.dequeue()));
                assertNull(bigQueue2.dequeue());

                bigQueue2.enqueue("world".getBytes());//将该数据添加到队列后面

                bigQueue2.flush();//强制保持队列的当前状态，

                assertTrue(bigQueue2.size() == 1L);
                assertTrue(!bigQueue2.isEmpty());

                assertEquals("world", new String(bigQueue2.dequeue()));//删除数据  检索和删除队列的前端
                assertNull(bigQueue2.dequeue());
            }
            bigQueue2.close();//该流并释放任何与系统相关的资源
        }
    }
@Test
    public void bigTest2() throws IOException {
       for (int i = 0;i<=10 ;i++){
            String str = "2017-07-31 09:43:20 insert into test(cityname,stationnum,pollutantcode,pollutantnum) values('西昌"+i+"',75,'no15',66)";
            bigQueue2.enqueue( str.getBytes() );
        }

        while(!bigQueue2.isEmpty()){
           byte[] by =  bigQueue2.dequeue();
            String str = new String(by);
            System.out.println(str);

           /* DefaultItemIterator dii = new DefaultItemIterator();
            bigQueue2.applyForEach(dii);
            System.out.println("[" + dii.getCount() + "] " + dii.toString());//统计计数，以及打印出元素*/
        }

    }
    bigController big = new bigController();
    @Test
    public void test2() throws IOException{
       // big.sqlDequeue();
    }


    @After
    public void clean() throws IOException {
        if (bigQueue2 != null) {
            bigQueue2.removeAll();
        }
    }


    private class DefaultItemIterator implements IBigQueue.ItemIterator {
        private long count = 0;
        private StringBuilder sb = new StringBuilder();

        public void forEach(byte[] item) throws IOException {
            try {
                if (count<20) {
                    sb.append(new String(item));
                    sb.append(", ");

                }
                count++;
            } catch (Exception e) {
                throw new IOException(e);
            }
        }

        public long getCount() {
            return count;
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }
}

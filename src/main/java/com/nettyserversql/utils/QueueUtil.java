package com.nettyserversql.utils;

import com.nettyserversql.bigqueue.BigQueueImpl;
import com.nettyserversql.bigqueue.IBigQueue;
import com.nettyserversql.bigqueue.utils.TestUtil;
import com.nettyserversql.entity.SqlVo;
import com.nettyserversql.service.SqlService;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/9.
 */
@Component
public class QueueUtil {

    @Autowired
    private SqlService sqlService;



    FileOutput fileOutput =new FileOutput();
    private String testDir = TestUtil.TEST_BASE_DIR + "bigqueue/unit";
    public static IBigQueue bigQueue;//从客户端接收到的数据保存的队列
    private IBigQueue bigQueue2;//执行失败10次之内保存的队列，
    public static IBigQueue bigQueue3;//失败10次之后的数据保存队列
    {
        try {
            bigQueue = new BigQueueImpl(testDir, "log_sql");
            bigQueue2 = new BigQueueImpl(testDir, "log_sql2");
            bigQueue3 = new BigQueueImpl(testDir, "log_sql3");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加元素到队列
     * @param str
     * @throws IOException
     */
    public void addQueue(String str) throws IOException {
        System.out.println("保存数据到队列");
        bigQueue.enqueue( str.getBytes() );
    }
    /**
     * 过渡数组，用于保存执行次数少于10次的sql语句
     * @param str
     * @throws IOException
     */
    public void addQueue2(String str) throws IOException {
        System.out.println("保存临时队列");
        bigQueue2.enqueue( str.getBytes() );
    }
    /**
     * 过渡数组，用于保存执行次数少于10次的sql语句
     * @param str
     * @throws IOException
     */
    public void addQueue3(String str) throws IOException {
        System.out.println("保存到失败队列");
        bigQueue3.enqueue( str.getBytes() );
    }



    /**
     * 解析出sql并执行
     * @throws IOException
     */
    int count = 0;
    public void sqlDequeue() throws IOException {
        System.out.println("执行");
        if (bigQueue2.isEmpty()){//bigQueue2为空时说明说明没有执行失败的sql语句

            while(!bigQueue.isEmpty()){
                byte[] by = bigQueue.dequeue();
                String str =new String( by );
                str = fileOutput.sqlIo(str);
                System.out.println("str--->"+str);

                Boolean bo = sqlService.appliquerSql( str );
                System.out.println("bo--->"+bo);
                //如果执行失败保存到bigQueue2的队列
                if(!bo){
                    addQueue2(str);
                    sqlDequeue();
                }
            }
        }else{//bigQueue2不为空时说明说明没有执行失败的sql语句

        while(!bigQueue2.isEmpty()){

            byte[] by = bigQueue2.dequeue();
            String str2 = new String(by);
            Boolean bo = sqlService.appliquerSql( str2 );
            if (!bo && count<=9){
                count ++;
                addQueue2(str2);//再次将sql语句保存到临时队列
                System.out.println("count ---->"+count);
            }else if (count >= 10){
                System.out.println("count ---->"+count);
                byte[] by3 = bigQueue2.dequeue();
               String str3 = new String(by3);

                addQueue3(str3);
            }

            sqlDequeue();//递归调用该方法
        }

        }

        System.out.println("队列为空");
    }

    @After
    public void clean() throws IOException {
        if (bigQueue != null) {
            bigQueue.removeAll();
        }
    }
    @After
    public void clean2() throws IOException{
        if (bigQueue2 != null) {
            bigQueue2.removeAll();
        }
    }
    @After
    public void clean3() throws IOException{
        if (bigQueue3 != null) {
            bigQueue3.removeAll();
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

package com.nettyserversql;

import com.nettyserversql.controller.bigController;
import com.nettyserversql.utils.QueueUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BigControllerTest {

    @Autowired
    private QueueUtil big;
    @Test
    public void test1() throws IOException {
      /*  String str1 = "2017-07-31 09:43:20 insert into test(cityname,stationnum,pollutantcode,pollutantnum) values('西昌'75 ,'no',66)";
        big.addQueue( str1 );*/
        for (int i = 0;i<=10 ;i++){
            String str = "2017-07-31 09:43:20 insert into test(cityname,stationnum,pollutantcode,pollutantnum) values('西昌"+i+"',75 ,'no"+i+"',66)";
            big.addQueue( str );
        }
    }
    @Test
    public void test2() throws IOException{
        big.sqlDequeue();
    }


}

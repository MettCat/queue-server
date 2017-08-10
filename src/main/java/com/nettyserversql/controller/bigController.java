package com.nettyserversql.controller;


import com.nettyserversql.service.SqlService;

import com.nettyserversql.utils.QueueUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**队列添加，取出元素
 *
 * Created by Administrator on 2017/8/9.
 */
@Component
@Configuration
@EnableScheduling
public class bigController {
   @Autowired
    private QueueUtil queueUtil;

    private final Logger logger = LoggerFactory.getLogger( bigController.class );
   @Scheduled(cron = "0/10 * * * * ?")
    public void executeUploadTask() throws IOException {
        Thread thread =new Thread(  );
        queueUtil.sqlDequeue();
        System.out.println("定时任务2:"+thread.getId());
    }


}

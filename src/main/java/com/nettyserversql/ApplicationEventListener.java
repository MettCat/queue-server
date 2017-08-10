package com.nettyserversql;


import com.nettyserversql.bigqueue.utils.TestUtil;
import com.nettyserversql.nettyserver.TimeServer;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Created by train on 17/5/17.
 */
public class ApplicationEventListener implements ApplicationListener {
    private String testDir = TestUtil.TEST_BASE_DIR + "bigqueue/unit";
    //public static IBigQueue bigQueue;
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        int port = 8083;
        // 在这里可以监听到Spring Boot的生命周期
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            // 初始化环境变量
        } else if (event instanceof ApplicationPreparedEvent) {
            // 初始化完成
        } else if (event instanceof ContextRefreshedEvent) {
            // 应用刷新
        } else if (event instanceof ApplicationReadyEvent) {
            try {
                new TimeServer().bind(port);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (event instanceof ContextStartedEvent) {
            //应用启动，需要在代码动态添加监听器才可捕获
        } else if (event instanceof ContextStoppedEvent) {
            // 应用停止
        } else if (event instanceof ContextClosedEvent) {
            // 应用关闭
        } else {

        }
    }
}

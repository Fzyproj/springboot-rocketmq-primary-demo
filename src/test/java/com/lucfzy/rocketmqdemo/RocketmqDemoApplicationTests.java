package com.lucfzy.rocketmqdemo;

import com.lucfzy.rocketmqdemo.service.ParamConfigService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RocketmqDemoApplicationTests {

    @Autowired
    private DefaultMQProducer defaultMQProducer;
    @Autowired
    private ParamConfigService paramConfigService;

    @Test
    void contextLoads() {
    }

    @Test
    void run(){
        System.out.println(defaultMQProducer);
        System.out.println(paramConfigService);
    }
}

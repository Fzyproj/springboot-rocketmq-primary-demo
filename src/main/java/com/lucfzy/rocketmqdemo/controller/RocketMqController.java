package com.lucfzy.rocketmqdemo.controller;

import com.lucfzy.rocketmqdemo.aop.Action;
import com.lucfzy.rocketmqdemo.consumer.ConsumerConfig;
import com.lucfzy.rocketmqdemo.listener.RocketMsgListener;
import com.lucfzy.rocketmqdemo.produce.ProducerConfig;
import com.lucfzy.rocketmqdemo.service.RocketMqService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.logging.Logger;

@RestController
public class RocketMqController {

    @Autowired
    private RocketMqService rocketMqService;
    @Autowired
    private ProducerConfig producerConfig;
    @Autowired
    private ConsumerConfig consumerConfig;
    private DefaultMQPushConsumer defaultMQPushConsumer;
    @Autowired
    private RocketMsgListener rocketMsgListener;
    @Autowired
    private static Logger logger;
    // producer产生的msgId
    private String msgId;

    // 启动生产者
    @GetMapping("/producer")
    public String startProducer() throws MQClientException {
        DefaultMQProducer rocketMQProducer = producerConfig.getRocketMQProducer();
        rocketMQProducer.start();
        return "生产者已启动。。。";
    }

    // 启动消费者
    @GetMapping("/consumer")
    public String startConsumer() throws MQClientException {
        defaultMQPushConsumer = consumerConfig.getRocketMQConsumer();
        defaultMQPushConsumer.start();
        return "消费者已启动。。。";
    }

    // 生产者发送
    @GetMapping("/send")
    public String sendMessage(@RequestParam("msg") String msgInfo) throws MQClientException {
        SendResult sendResult = rocketMqService.openAccountMsg(msgInfo);
        this.msgId = sendResult.getMsgId();
        System.out.println(sendResult.toString());
        Set<MessageQueue> cicadaTopic = defaultMQPushConsumer.fetchSubscribeMessageQueues("CicadaTopic");
        for (MessageQueue messageQueue : cicadaTopic) {
            System.out.println(messageQueue);
        }
        return sendResult.getMsgId();
    }
//    @GetMapping("/comsume")
//    public String consumeMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
//        List<MessageExt> msgList = new ArrayList<>();
//        msgList.add( defaultMQPushConsumer.viewMessage("CicadaTopic", msgId));
//        defaultMQPushConsumer.registerMessageListener(rocketMsgListener);
//    }
}

package com.lucfzy.rocketmqdemo.consumer;

import com.lucfzy.rocketmqdemo.listener.RocketMsgListener;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 消费者bean
 */
@Configuration
public class ConsumerConfig {
    // 设置日志记录
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerConfig.class) ;
    // 设置namesrv地址
    @Value("${rocketmq.consumer.namesrvAddr}")
    private String namesrvAddr;
    // 设置组名称
    @Value("${rocketmq.consumer.groupName}")
    private String groupName;
    // 设置消费最小线程数
    @Value("${rocketmq.consumer.consumeThreadMin}")
    private int consumeThreadMin;
    // 设置消费最大线程
    @Value("${rocketmq.consumer.consumeThreadMax}")
    private int consumeThreadMax;
    // 主题名
    @Value("${rocketmq.consumer.topics}")
    private String topics;
    // 可以绑定的消息队列的最大个数
    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    private int consumeMessageBatchMaxSize;
    // 注入消息监听器，监听消息队列更新情况
    @Resource
    private RocketMsgListener msgListener;

    // 获得DefaultMQPushConsumer对象
    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer(){
        // 构造DefaultMQPushConsumer并设置组name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        // 传入上面的参数
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        // 非常关键的一步，注册监听器。
        consumer.registerMessageListener(msgListener);
        //---
        try {
            // 分割topics。eg:"a~aa";"b~bb";"c~cc"
            String[] topicTagsArr = topics.split(";");
            // 遍历topics
            for (String topicTags : topicTagsArr) { // a~aa
                String[] topicTag = topicTags.split("~"); // String[] topicTag = {"a","aa"};
                // 前者是主题名，后者是感兴趣流tag标签
                consumer.subscribe(topicTag[0],topicTag[1]); // consumer.subscribe("a","aa");
            }
//            // 启动消费者
//            consumer.start();
        }catch (MQClientException e){
            e.printStackTrace();
        }
        // 返回Consumer对象
        return consumer;
    }
}

package com.lucfzy.rocketmqdemo.service;

import org.apache.rocketmq.client.producer.SendResult;

public interface RocketMqService {
    public SendResult openAccountMsg(String msgInfo);
}

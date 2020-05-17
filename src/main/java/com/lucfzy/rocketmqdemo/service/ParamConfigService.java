package com.lucfzy.rocketmqdemo.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ParamConfigService {
    @Value("${rocket.group}")
    public String rocketGroup ;
    @Value("${rocket.topic}")
    public String rocketTopic ;
    @Value("${rocket.tag}")
    public String rocketTag ;
}

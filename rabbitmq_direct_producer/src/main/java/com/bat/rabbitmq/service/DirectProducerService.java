package com.bat.rabbitmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        //第一个参数为队列名称，第二个为发送内容
        amqpTemplate.convertAndSend("direct.test","Hello Rabbitmq!");
    }
}

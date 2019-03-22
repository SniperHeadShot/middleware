package com.bat.rabbitmq.service;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendOne() {
        amqpTemplate.convertAndSend(Constant.EXCHANGE_NAME_ONE, Constant.ROUTING_KEY_ONE, "ROUTING_KEY_ONE Hello Rabbitmq Direct!!!");
    }

    public void sendTwo() {
        amqpTemplate.convertAndSend(Constant.EXCHANGE_NAME_TWO, Constant.ROUTING_KEY_TWO, "ROUTING_KEY_TWO Hello Rabbitmq Direct!!!");
    }
}

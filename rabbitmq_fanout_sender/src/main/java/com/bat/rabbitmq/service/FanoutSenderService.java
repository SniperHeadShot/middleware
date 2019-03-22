package com.bat.rabbitmq.service;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutSenderService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void fanoutSendMes() {
        this.amqpTemplate.convertAndSend(Constant.EXCHANGE_NAME, "", "Hello Fanout!!!");
    }

}

package com.bat.rabbitmq.config.exchange;

import org.springframework.amqp.core.TopicExchange;

public class ExchangeTypeTwo extends TopicExchange {

    public ExchangeTypeTwo(String name) {
        super(name);
    }
}

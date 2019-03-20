package com.bat.rabbitmq.config.exchange;

import org.springframework.amqp.core.TopicExchange;

public class ExchangeTypeOne extends TopicExchange {

    public ExchangeTypeOne(String name) {
        super(name);
    }
}

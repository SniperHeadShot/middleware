package com.bat.rabbitmq.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqExchangeConfig {

    @Bean
    public TopicExchange exchangeTypeOne() {
        return new TopicExchange(Constant.EXCHANGE_NAME_ONE);
    }

    @Bean
    public TopicExchange exchangeTypeTwo() {
        return new TopicExchange(Constant.EXCHANGE_NAME_TWO);
    }
}

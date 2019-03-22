package com.bat.rabbitmq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqServerConfig {

    @Bean
    public DirectExchange exchangeOne() {
        return new DirectExchange(Constant.EXCHANGE_NAME_ONE);
    }

    @Bean
    public DirectExchange exchangeTwo() {
        return new DirectExchange(Constant.EXCHANGE_NAME_TWO);
    }
}

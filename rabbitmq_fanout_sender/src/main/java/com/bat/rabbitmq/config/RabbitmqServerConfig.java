package com.bat.rabbitmq.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqServerConfig {

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(Constant.EXCHANGE_NAME);
    }
}

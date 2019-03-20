package com.bat.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqQueueConfig {

    @Bean
    public Queue testQueue(){
        return new Queue("direct.test");
    }

}

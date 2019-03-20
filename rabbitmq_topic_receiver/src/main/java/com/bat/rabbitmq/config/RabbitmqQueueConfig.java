package com.bat.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqQueueConfig {

    @Bean
    public Queue topicOneQueueTestOne(){
        return new Queue("topic.one.test.one");
    }

    @Bean
    public Queue topicOneQueueTestTwo(){
        return new Queue("topic.one.test.two");
    }

    @Bean
    public Queue topicTwoQueueTestA(){
        return new Queue("topic.two.a");
    }

    @Bean
    public Queue topicTwoQueueTestB(){
        return new Queue("topic.two.b");
    }
}

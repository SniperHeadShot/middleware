package com.bat.rabbitmq.config;

import com.bat.rabbitmq.config.exchange.ExchangeTypeOne;
import com.bat.rabbitmq.config.exchange.ExchangeTypeTwo;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqExchangeConfig {

    /*
     *
     * 定义队列
     *
     */
    @Bean
    public Queue queueTopicOne() {
        return new Queue("topic.one");
    }

    @Bean
    public Queue queueTopicTwo() {
        return new Queue("topic.two");
    }

    /*
     *
     * 定义交换机
     *
     */

    @Bean
    public TopicExchange exchangeTypeOne() {
        return new ExchangeTypeOne("exchange.topic.one");
    }

    @Bean
    public TopicExchange exchangeTypeTwo() {
        return new ExchangeTypeTwo("exchange.topic.two");
    }

    /*
     *
     * 将队列与交换机匹配并设置路由
     *
     */

    @Bean
    Binding bindingExchangeTypeOne(Queue queueTopicOne, TopicExchange exchangeTypeOne) {
        return BindingBuilder.bind(queueTopicOne).to(exchangeTypeOne).with("topic.one.#");
    }

    @Bean
    Binding bindingExchangeTypeTwo(Queue queueTopicTwo, TopicExchange exchangeTypeTwo) {
        return BindingBuilder.bind(queueTopicTwo).to(exchangeTypeTwo).with("topic.two.*");
    }
}

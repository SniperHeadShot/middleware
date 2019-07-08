package com.bat.rabbitmq.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

/**
 * Rabbitmq 配置
 *
 * @author ZhengYu
 * @version 1.0 2019/7/8 15:57
 **/
@Configuration
public class CloudRabbitmqConfig {

    /**
     * 配置环境1的Rabbitmq
     */
    @Bean(name = "cloudConnectionFactory")
    @Primary
    public ConnectionFactory cloudConnectionFactory(
            @Value("${spring.rabbitmq.cloud.host}") String host,
            @Value("${spring.rabbitmq.cloud.port}") int port,
            @Value("${spring.rabbitmq.cloud.username}") String username,
            @Value("${spring.rabbitmq.cloud.password}") String password,
            @Value("${spring.rabbitmq.cloud.virtualHost}") String virtualHost) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean(name = "cloudRabbitListenerContainerFactory")
    @Primary
    public RabbitListenerContainerFactory<?> cloudRabbitListenerContainerFactory(@Qualifier("cloudConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        // 设置消费者线程数
        simpleRabbitListenerContainerFactory.setConcurrentConsumers(5);
        // 设置最大消费者线程数
        simpleRabbitListenerContainerFactory.setMaxConcurrentConsumers(10);
        // 设置消费者标签
        simpleRabbitListenerContainerFactory.setConsumerTagStrategy(tagStr -> "云环境");
        return simpleRabbitListenerContainerFactory;
    }

    @Bean("cloudRabbitAdmin")
    @Primary
    public RabbitAdmin cloudRabbitAdmin(@Qualifier("cloudConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // Exchange 参数
        Map<String, Object> arguments = new HashMap<>(1);
        arguments.put("x-message-ttl", 900);
        // 新建 TopicExchange
        TopicExchange exchange = new TopicExchange("exchange.cloud.scenes", true, false, arguments);
        rabbitAdmin.declareExchange(exchange);
        // 新建 Queue
        Queue queue = new Queue("queue.cloud.scenes");
        rabbitAdmin.declareQueue(queue);
        // 绑定 Exchange 和 RoutingKey
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("topic.cloud.scenes"));
        return rabbitAdmin;
    }

    @Bean(name = "cloudRabbitTemplate")
    public RabbitTemplate cloudRabbitTemplate(@Qualifier("cloudConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    /**
     * 配置环境2的Rabbitmq
     */
    @Bean(name = "localConnectionFactory")
    public ConnectionFactory localConnectionFactory(
            @Value("${spring.rabbitmq.local.host}") String host,
            @Value("${spring.rabbitmq.local.port}") int port,
            @Value("${spring.rabbitmq.local.username}") String username,
            @Value("${spring.rabbitmq.local.password}") String password,
            @Value("${spring.rabbitmq.local.virtualHost}") String virtualHost) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean(name = "localListenerContainerFactory")
    public RabbitListenerContainerFactory<?> localRabbitListenerContainerFactory(@Qualifier("localConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        // 设置消费者线程数
        simpleRabbitListenerContainerFactory.setConcurrentConsumers(5);
        // 设置最大消费者线程数
        simpleRabbitListenerContainerFactory.setMaxConcurrentConsumers(10);
        // 设置消费者标签
        simpleRabbitListenerContainerFactory.setConsumerTagStrategy(tagStr -> "本地环境");
        return simpleRabbitListenerContainerFactory;
    }

    @Bean("localRabbitAdmin")
    public RabbitAdmin localRabbitAdmin(@Qualifier("localConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // Exchange 参数
        Map<String, Object> arguments = new HashMap<>(1);
        arguments.put("x-message-ttl", 900);
        // 新建 TopicExchange
        TopicExchange exchange = new TopicExchange("exchange.local.scenes", true, false, arguments);
        rabbitAdmin.declareExchange(exchange);
        // 新建 Queue
        Queue queue = new Queue("queue.local.scenes");
        rabbitAdmin.declareQueue(queue);
        // 绑定 Exchange 和 RoutingKey
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("topic.local.scenes"));
        return rabbitAdmin;
    }

    @Bean(name = "localRabbitTemplate")
    public RabbitTemplate localRabbitTemplate(@Qualifier("localConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}

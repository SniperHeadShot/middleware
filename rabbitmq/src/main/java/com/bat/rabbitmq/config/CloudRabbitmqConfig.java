package com.bat.rabbitmq.config;

import com.bat.rabbitmq.enums.RabbitmqBaseConfigEnum;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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
        return buildConnectionFactory(host, port, username, password, virtualHost);
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

    @Bean(name = "cloudDlxRabbitListenerContainerFactory")
    @Primary
    public RabbitListenerContainerFactory<?> cloudDlxRabbitListenerContainerFactory(@Qualifier("cloudConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        // 设置消费者线程数
        simpleRabbitListenerContainerFactory.setConcurrentConsumers(5);
        // 设置最大消费者线程数
        simpleRabbitListenerContainerFactory.setMaxConcurrentConsumers(10);
        // 设置消费者标签
        simpleRabbitListenerContainerFactory.setConsumerTagStrategy(tagStr -> "云环境 死信队列");
        return simpleRabbitListenerContainerFactory;
    }

    @Bean("cloudRabbitAdmin")
    @Primary
    public RabbitAdmin cloudRabbitAdmin(@Qualifier("cloudConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 死信队列配置
        DirectExchange dlxExchange = new DirectExchange(RabbitmqBaseConfigEnum.EXCHANGE_CLOUD_DLX.getEnName());
        rabbitAdmin.declareExchange(dlxExchange);

        // 缓冲队列配置 携带过期时间的消息在到期后会发送到死信交换机
        Map<String, Object> dlxArguments = new HashMap<>(2);
        dlxArguments.put("x-dead-letter-exchange", RabbitmqBaseConfigEnum.EXCHANGE_CLOUD_DLX.getEnName());
        dlxArguments.put("x-dead-letter-routing-key", RabbitmqBaseConfigEnum.TOPIC_CLOUD_SCENES.getEnName());
        // durable     持久化消息队列, rabbitmq重启的时候不需要创建新的队列 默认true
        // auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
        // exclusive   表示该消息队列是否只在当前connection生效,默认是false
        Queue bufferQueue = new Queue(RabbitmqBaseConfigEnum.QUEUE_CLOUD_DELAY.getEnName(), true, false, false, dlxArguments);
        rabbitAdmin.declareQueue(bufferQueue);

        // 真实处理消息队列配置
        TopicExchange cloudExchange = new TopicExchange(RabbitmqBaseConfigEnum.EXCHANGE_CLOUD_SCENES.getEnName(), true, false);
        rabbitAdmin.declareExchange(cloudExchange);
        Queue cloudQueue = new Queue(RabbitmqBaseConfigEnum.QUEUE_CLOUD_SCENES.getEnName(), true, false, false);
        rabbitAdmin.declareQueue(cloudQueue);

        // 绑定
        rabbitAdmin.declareBinding(BindingBuilder.bind(cloudQueue).to(cloudExchange).with(RabbitmqBaseConfigEnum.TOPIC_CLOUD_SCENES.getEnName()));
        rabbitAdmin.declareBinding(BindingBuilder.bind(bufferQueue).to(cloudExchange).with(RabbitmqBaseConfigEnum.TOPIC_CLOUD_DELAY.getEnName()));
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
        return buildConnectionFactory(host, port, username, password, virtualHost);
    }

    @Bean(name = "localRabbitListenerContainerFactory")
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
        TopicExchange exchange = new TopicExchange(RabbitmqBaseConfigEnum.EXCHANGE_LOCAL_SCENES.getEnName(), true, false, arguments);
        rabbitAdmin.declareExchange(exchange);
        // 新建 Queue
        Queue queue = new Queue(RabbitmqBaseConfigEnum.QUEUE_LOCAL_SCENES.getEnName());
        rabbitAdmin.declareQueue(queue);
        // 绑定 Exchange 和 RoutingKey
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(RabbitmqBaseConfigEnum.TOPIC_LOCAL_SCENES.getEnName()));
        return rabbitAdmin;
    }

    @Bean(name = "localRabbitTemplate")
    public RabbitTemplate localRabbitTemplate(@Qualifier("localConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    private ConnectionFactory buildConnectionFactory(String host, int port, String username, String password, String virtualHost) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }
}

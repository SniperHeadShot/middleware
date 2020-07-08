package com.bat.rabbitmq.consumer.config;

import com.bat.rabbitmq.consumer.entity.BindingMetadata;
import com.bat.rabbitmq.consumer.entity.ExchangeMetadata;
import com.bat.rabbitmq.consumer.entity.QueueMetadata;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Rabbitmq 通道生命周期管理类
 *
 * @author ZhengYu
 * @version 1.0 2020/7/8 23:24
 **/
@Slf4j
public class RabbitmqConnectionHolder {

    private ConnectionFactory connectionFactory;

    private Connection connection;

    private Channel channel;

    private List<ExchangeMetadata> EXCHANGE_CACHE_LIST = new LinkedList<>();

    private List<QueueMetadata> QUEUE_CACHE_LIST = new LinkedList<>();

    private List<BindingMetadata> BINDING_CACHE_LIST = new LinkedList<>();

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    static {
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
                5,
                10,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setNameFormat("thread-pool-rabbitmq-holder-%d").build(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
    }

    public RabbitmqConnectionHolder(String host, Integer port, String username, String password, String virtualHost) {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);

        // 禁用自带的重试机制
        connectionFactory.setAutomaticRecoveryEnabled(false);
    }

    public void flushConsumer() {
        buildRabbitmqConnectionWithRetry();
    }

    private void buildRabbitmqConnectionWithRetry() {
        // 建立连接
        boolean connectSuccessFlag;
        do {
            connectSuccessFlag = createConnection();
            if (!connectSuccessFlag) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    log.warn("Rabbit 重试中断异常, 原因: [{}] [{}]", e.getMessage(), e);
                }
            }
        } while (!connectSuccessFlag);

        // 声明交换机、队列和绑定关系
        declareMetadata();

        // 建立消费者
        buildConsumer();
    }

    private boolean createConnection() {
        try {
            if (connection == null || !connection.isOpen()) {
                connection = connectionFactory.newConnection();

                // 这里可以使用短信通知
                connection.addBlockedListener(new BlockedListener() {

                    @Override
                    public void handleBlocked(String reason) {
                        log.warn("Rabbit 内存或磁盘空间不足产生报警");
                    }

                    @Override
                    public void handleUnblocked() {
                        log.warn("Rabbit 内存或磁盘空间不足报警接触");
                    }
                });
            }
            createChannel();
            return true;
        } catch (Exception e) {
            log.error("创建Rabbitmq Connection 失败, 原因: [{}] [{}]", e.getMessage(), e);
        }
        return false;
    }

    private void createChannel(){
        if (channel == null || !channel.isOpen()) {
            try {
                channel = connection.createChannel();

                // 添加连接断开监听类
                channel.addShutdownListener(shutdownSignalException -> {
                    log.error("rabbitmq 连接断开, 原因: [{}] [{}]", shutdownSignalException.getMessage(), shutdownSignalException);
                    decreaseConsumer();
                    flushConsumer();
                });
            } catch (IOException e) {
                log.error("创建Rabbitmq Channel 失败, 原因: [{}] [{}]", e.getMessage(), e);
            }
        }
    }

    /**
     * 声明交换机、队列和绑定关系
     *
     * @author ZhengYu
     */
    private void declareMetadata() {
        EXCHANGE_CACHE_LIST.forEach(exchangeMetadata -> {
            try {
                channel.exchangeDeclare(exchangeMetadata.getExchangeName(), exchangeMetadata.getExchangeType());
            } catch (IOException e) {
                log.error("rabbitmq 申明交换机 [{}]-[{}] 失败, 原因: [{}] [{}]", exchangeMetadata.getExchangeName(), exchangeMetadata.getExchangeType(), e.getMessage(), e);
            }
        });
        QUEUE_CACHE_LIST.forEach(queueMetadata -> {
            try {
                channel.queueDeclare(queueMetadata.getQueueName(), queueMetadata.isDurable(), queueMetadata.isExclusive(), queueMetadata.isAutoDelete(), queueMetadata.getArguments());
            } catch (IOException e) {
                log.error("rabbitmq 申明队列 [{}]] 失败, 原因: [{}] [{}]", queueMetadata.getQueueName(), e.getMessage(), e);
            }
        });
        BINDING_CACHE_LIST.forEach(bindingMetadata -> {
            try {
                channel.exchangeBind(bindingMetadata.getExchangeName(), bindingMetadata.getQueueName(), bindingMetadata.getRoutingKey());
            } catch (IOException e) {
                log.error("rabbitmq 申明绑定关系 [{}]-[{}]-[{}] 失败, 原因: [{}] [{}]", bindingMetadata.getQueueName(), bindingMetadata.getExchangeName(), bindingMetadata.getRoutingKey(), e.getMessage(), e);
            }
        });
    }

    private void decreaseConsumer() {
        QUEUE_CACHE_LIST.stream()
                .filter(queueMetadata -> queueMetadata.getConsumerFallback() != null)
                .filter(queueMetadata -> queueMetadata.getReferenceCounter().get() > 0)
                .forEach(queueMetadata -> queueMetadata.getReferenceCounter().decrementAndGet());
    }

    // 建立消费者
    private void buildConsumer() {
        QUEUE_CACHE_LIST.stream()
                .filter(queueMetadata -> queueMetadata.getConsumerFallback() != null)
                .filter(queueMetadata -> queueMetadata.getReferenceCounter().get() == 0)
                .forEach(queueMetadata -> THREAD_POOL_EXECUTOR.execute(() -> {
                    try {
                        channel.basicConsume(queueMetadata.getQueueName(), true, new DefaultConsumer(channel) {

                            @Override
                            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                                try {
                                    queueMetadata.getConsumerFallback().consumerMsg(consumerTag, envelope, properties, body);
                                } catch (Exception e) {
                                    log.error("队列 [{}] 处理消息 [{}] 异常, 原因: [{}] [{}]", queueMetadata.getQueueName(), new String(body), e.getMessage(), e);
                                }
                            }
                        });
                        queueMetadata.getReferenceCounter().incrementAndGet();
                    } catch (IOException e) {
                        log.error("rabbitmq 申明队列 [{}]] 失败, 原因: [{}] [{}]", queueMetadata.getQueueName(), e.getMessage(), e);
                    }
                }));
    }

    public void addExchangeMetadata(ExchangeMetadata... exchangeMetadata) {
        EXCHANGE_CACHE_LIST.addAll(Arrays.asList(exchangeMetadata));
    }

    public void addQueueMetadata(QueueMetadata... queueMetadata) {
        QUEUE_CACHE_LIST.addAll(Arrays.asList(queueMetadata));
    }

    public void addBindingMetadata(BindingMetadata... bindingMetadata) {
        BINDING_CACHE_LIST.addAll(Arrays.asList(bindingMetadata));
    }
}

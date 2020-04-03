package com.bat.rabbitmq.publisher.confirm;

import com.bat.bean.entity.RabbitmqLinkInfo;
import com.bat.bean.util.RabbitmqConfigUtil;
import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.DefaultExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

/**
 * Rabbitmq Confirm 生产者
 *
 * @author ZhengYu
 * @version 1.0 2020/4/1 18:49
 **/
@Slf4j
public class ConfirmPublisher {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        RabbitmqLinkInfo cloudRabbitmqLinkInfo = RabbitmqConfigUtil.getCloudRabbitmqLinkInfo();

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(cloudRabbitmqLinkInfo.getHost());
        factory.setPort(cloudRabbitmqLinkInfo.getPort());
        factory.setUsername(cloudRabbitmqLinkInfo.getUsername());
        factory.setPassword(cloudRabbitmqLinkInfo.getPassword());
        factory.setVirtualHost(cloudRabbitmqLinkInfo.getVirtualHost());
        // 设置异常捕获
        factory.setExceptionHandler(new DefaultExceptionHandler());

        // 创建连接
        Connection connection = factory.newConnection();

        // 创建信道
        Channel channel = connection.createChannel();

        // 声明交换机和队列
        channel.exchangeDeclare("v1.exchange.confirm", "topic");
        channel.queueDeclare("v1.queue.confirm", false, false, false, null);
        channel.queueBind("v1.queue.confirm", "v1.exchange.confirm", "v1.routing.key.confirm");

        // 开启confirm
        channel.confirmSelect();

        // 监听每条消息 ack执行handleAck, nack执行handleNack
        channel.addConfirmListener(new ConfirmListener() {

            @Override
            public void handleAck(long deliveryTag, boolean multiple) {
                log.info("handleAck deliveryTag=[{}], multiple=[{}]", deliveryTag, multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) {
                log.info("handleAck deliveryTag=[{}], multiple=[{}]", deliveryTag, multiple);
            }
        });

        final long start = System.currentTimeMillis();
        IntStream.rangeClosed(1, 5).forEach(index -> {
            try {
                channel.basicPublish("v1.exchange.confirm", "v1.routing.key.confirm", MessageProperties.PERSISTENT_BASIC, String.format("Confirm --> [%d]", index).getBytes());
            } catch (IOException e) {
                log.error("发送消息异常 [{}]", e);
            }
        });
        if (channel.waitForConfirms()) {
            log.info("发送成功 ...");
        } else {
            log.info("后补重试操作 ...");
        }
        log.info("耗时:[{}ms]", System.currentTimeMillis() - start);

        // 被动声明(如果不存在就抛出异常)
        // AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclarePassive("v1.exchange.confirm");
        // AMQP.Queue.DeclareOk declareOkQueue = channel.queueDeclarePassive("v1.queue.confirm");
        // log.info("declare queue passively ==> messageCount=[{}], consumerCount=[{}]", declareOkQueue.getMessageCount(), declareOkQueue.getConsumerCount());

        // byte[] messageBytes = "Hello RabbitMQ!".getBytes();
        // 发送消息, 指定消息类型
        // channel.basicPublish("v1.exchange.confirm", "v1.routing.key.confirm", true, MessageProperties.PERSISTENT_TEXT_PLAIN, messageBytes);
        // 发送消息, 自定义格外参数配置
//        channel.basicPublish("v1.exchange.confirm", "v1.routing.key.confirm",
//                new AMQP.BasicProperties.Builder()
//                        .contentType("text/plain")
//                        .deliveryMode(2)
//                        .priority(2)
//                        .messageId("08fae261f6a5406db5a0e27ff4b22322")
//                        .timestamp(new Date())
//                        .headers(createMsgHeader())
//                        .build(),
//                messageBytes);

        // 关闭流
        // channel.close();
        // connection.close();
    }
}

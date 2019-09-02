package com.bat.rabbitmq.nativemode;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * 原生方式连接 RabbitMQ
 *
 * @author ZhengYu
 * @version 1.0 2019/8/17 19:25
 **/
@Slf4j
public class NativeModeTestMain {
    public static void main(String[] args) {
        String config = (String)System.getProperties().get("config");
        System.out.println(config);
        Connection connection = getRabbitmqConnectionMethodTwo();
        receiverMsg(connection);
        publishMsg(connection);
    }

    /**
     * 获取连接方式1
     *
     * @return com.rabbitmq.client.Connection
     * @author ZhengYu
     */
    private static Connection getRabbitmqConnectionMethodOne() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("101.132.45.166");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("yushen");
        connectionFactory.setPassword("123123");
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        try {
            connection = connectionFactory.newConnection();
        } catch (TimeoutException | IOException e) {
            log.info("错误的连接信息为: [{}]", e);
        }
        return connection;
    }

    /**
     * 获取连接方式2
     *
     * @return com.rabbitmq.client.Connection
     * @author ZhengYu
     */
    private static Connection getRabbitmqConnectionMethodTwo() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        String uri = String.format("amqp://%s:%s@%s:%d%s", "yushen", "123123", "101.132.45.166", 5672, "/");
        Connection connection = null;
        try {
            connectionFactory.setUri(uri);
            connectionFactory.setVirtualHost("/");
            connection = connectionFactory.newConnection();
        } catch (Exception e) {
            log.info("错误的连接信息为: [{}]", e);
        }
        return connection;
    }

    /**
     * 发送消息
     *
     * @param connection 通道
     * @author ZhengYu
     */
    private static void publishMsg(Connection connection) {
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("v1.test.exchange", BuiltinExchangeType.TOPIC);

            channel.basicPublish("v1.test.exchange", "v1.test.routing.key", null, "测试发送".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.info("发送消息出错: [{}]", e);
        }
    }

    /**
     * 接收消息
     *
     * @param connection 接收消息
     * @author ZhengYu
     */
    private static void receiverMsg(Connection connection) {
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare("v1.test.queue", false, false, false, null);
            channel.queueBind("v1.test.queue", "v1.test.exchange", "v1.test.routing.key");

            channel.basicConsume("v1.test.queue", false, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    String content = new String(body, "utf-8");
                    System.out.println("消息正文：" + content + " routingKey " + routingKey + " contentType " + contentType);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            });
        } catch (IOException e) {
            log.info("接收消息出错: [{}]", e);
        }
    }
}

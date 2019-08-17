package com.bat.rabbitmq.nativemode;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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

    private static void publishMsg(Connection connection){
        try {
            Channel channel = connection.createChannel();
            // TODO https://www.cnblogs.com/vipstone/p/9275256.html
        } catch (IOException e) {
            log.info("错误的连接信息为: [{}]", e);
        }

    }


}

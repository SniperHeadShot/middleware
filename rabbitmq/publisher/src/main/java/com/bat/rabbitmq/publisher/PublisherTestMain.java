package com.bat.rabbitmq.publisher;

import com.bat.bean.entity.RabbitmqLinkInfo;
import com.bat.bean.util.RabbitmqConfigUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.DefaultExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Rabbitmq 生产者
 *
 * @author ZhengYu
 * @version 1.0 2020/4/1 18:49
 **/
@Slf4j
public class PublisherTestMain {
    public static void main(String[] args) throws IOException, TimeoutException {
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

        // 被动声明(如果不存在就抛出异常)
        // AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclarePassive("v1.exchange.confirm");
        // AMQP.Queue.DeclareOk declareOkQueue = channel.queueDeclarePassive("v1.queue.confirm");
        // log.info("declare queue passively ==> messageCount=[{}], consumerCount=[{}]", declareOkQueue.getMessageCount(), declareOkQueue.getConsumerCount());

        // 声明交换机和队列
        channel.exchangeDeclare("v1.exchange.confirm", "topic");
        channel.queueBind("v1.queue.confirm", "v1.exchange.confirm", "v1.routing.key.confirm");

        byte[] messageBytes = "Hello RabbitMQ!".getBytes();
        // 发送消息, 指定消息类型
        // channel.basicPublish("v1.exchange.confirm", "v1.routing.key.confirm", true, MessageProperties.PERSISTENT_TEXT_PLAIN, messageBytes);
        // 发送消息, 自定义格外参数配置
        channel.basicPublish("v1.exchange.confirm", "v1.routing.key.confirm",
                new AMQP.BasicProperties.Builder()
                        .contentType("text/plain")
                        .deliveryMode(2)
                        .priority(2)
                        .messageId("08fae261f6a5406db5a0e27ff4b22322")
                        .timestamp(new Date())
                        .headers(createMsgHeader())
                        .build(),
                messageBytes);

        // 关闭流
        channel.close();
        connection.close();
    }

    private static Map<String, Object> createMsgHeader() {
        return new HashMap<String, Object>(2) {{
            put("name", "SniperHeadShot");
            put("age", 25);
        }};
    }
}

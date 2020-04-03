package com.bat.rabbitmq.consumer.confirm;

import com.bat.bean.config.DateConstant;
import com.bat.bean.entity.RabbitmqLinkInfo;
import com.bat.bean.util.RabbitmqConfigUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

/**
 * Rabbitmq 消费者
 *
 * @author ZhengYu
 * @version 1.0 2020/4/1 20:51
 **/
@Slf4j
public class ConfirmConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitmqLinkInfo cloudRabbitmqLinkInfo = RabbitmqConfigUtil.getCloudRabbitmqLinkInfo();

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(cloudRabbitmqLinkInfo.getHost());
        factory.setPort(cloudRabbitmqLinkInfo.getPort());
        factory.setUsername(cloudRabbitmqLinkInfo.getUsername());
        factory.setPassword(cloudRabbitmqLinkInfo.getPassword());
        factory.setVirtualHost(cloudRabbitmqLinkInfo.getVirtualHost());

        // 创建连接
        Connection connection = factory.newConnection();

        // 创建信道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare("v1.queue.confirm", false, false, false, null);
        channel.queueBind("v1.queue.confirm", "v1.exchange.confirm", "v1.routing.key.confirm");

        // 消费消息
        channel.basicConsume("v1.queue.confirm", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                log.info("Confirm Consumer Msg=[{}], date=[{}]", new String(body), LocalDateTime.now().format(DateConstant.DATE_TIME_FORMATTER));

                // 手动确认
                try {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (IOException e) {
                    log.error("消息确认失败: [{}]", e);
                }
            }
        });
    }
}

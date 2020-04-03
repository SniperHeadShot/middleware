package com.bat.rabbitmq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.bat.bean.entity.RabbitmqLinkInfo;
import com.bat.bean.util.RabbitmqConfigUtil;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Rabbitmq 消费者
 *
 * @author ZhengYu
 * @version 1.0 2020/4/1 20:51
 **/
@Slf4j
public class ConsumerTestMain {
    public static void main(String[] args) throws IOException, TimeoutException {



        RabbitmqLinkInfo cloudRabbitmqLinkInfo = RabbitmqConfigUtil.getCloudRabbitmqLinkInfo();

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(cloudRabbitmqLinkInfo.getHost());
        factory.setPort(cloudRabbitmqLinkInfo.getPort());
        factory.setUsername(cloudRabbitmqLinkInfo.getUsername());
        factory.setPassword(cloudRabbitmqLinkInfo.getPassword());
        factory.setVirtualHost(cloudRabbitmqLinkInfo.getVirtualHost());
        factory.setSharedExecutor(Executors.newFixedThreadPool(5));

        // 设置恢复间隔，默认5秒
        factory.setNetworkRecoveryInterval(10000);

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
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String routingKey = envelope.getRoutingKey();
                long deliveryTag = envelope.getDeliveryTag();

                Map<String, Object> headers = properties.getHeaders();
                String contentType = properties.getContentType();
                log.info("handleDelivery routingKey=[{}], deliveryTag=[{}], contentType=[{}], headers=[{}]", routingKey, deliveryTag, JSONObject.toJSONString(headers), contentType);

                log.info("handleDelivery message=[{}]", new String(body));

                channel.basicAck(deliveryTag, false);
            }

            @Override
            public void handleConsumeOk(String consumerTag) {
                log.info("handleConsumeOk consumerTag=[{}]", consumerTag);
            }

            @Override
            public void handleCancelOk(String consumerTag) {
                log.info("handleCancelOk consumerTag=[{}]", consumerTag);
            }

            @Override
            public void handleCancel(String consumerTag) {
                log.info("handleCancel consumerTag=[{}]", consumerTag);
            }

            @Override
            public void handleRecoverOk(String consumerTag) {
                log.info("handleRecoverOk consumerTag=[{}]", consumerTag);
            }

            @Override
            public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                log.info("handleShutdownSignal consumerTag=[{}]", consumerTag);
            }
        });

        // 监听关闭状态
        connection.addShutdownListener(cause -> log.info("connection shutdown ==> [{}] [{}]", cause.getReason(), cause.isHardError()));
        channel.addShutdownListener(cause -> log.info("channel shutdown ==> [{}]", cause.getReason()));

        // 监听

        // 处理无法路由的消息
        // channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> log.info("ReturnListener replyCode=[{}], body=[{}]", replyCode, new String(body)));
    }
}

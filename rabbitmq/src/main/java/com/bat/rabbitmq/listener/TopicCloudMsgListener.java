package com.bat.rabbitmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Rabbitmq 客户端监听
 *
 * @author ZhengYu
 * @version 1.0 2019/7/5 17:21
 **/
@Component
@RabbitListener(
        containerFactory = "cloudRabbitListenerContainerFactory",
        bindings = @QueueBinding(
                value = @Queue(value = "queue.cloud.scenes", durable = "true"),
                exchange = @Exchange(value = "exchange.cloud.scenes", type = "topic"),
                key = "topic.cloud.scenes"
        ))
@Slf4j
public class TopicCloudMsgListener {

    @RabbitHandler
    public void msgListener(String str) {
        log.info("TopicCloudMsgListener 已接收： [{}]", str);
    }
}

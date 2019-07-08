package com.bat.rabbitmq.listener;

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
        containerFactory = "localListenerContainerFactory",
        bindings = @QueueBinding(
                value = @Queue(value = "queue.local.scenes", durable = "true"),
                exchange = @Exchange(value = "exchange.local.scenes", type = "topic"),
                key = "topic.local.scenes"
        ))
public class TopicLocalMsgListener {

    @RabbitHandler
    public void msgListener(String str) {
        System.out.println("TopicLocalMsgListener 已接收：" + str);
    }
}

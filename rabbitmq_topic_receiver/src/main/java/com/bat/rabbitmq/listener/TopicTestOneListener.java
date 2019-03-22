package com.bat.rabbitmq.listener;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = Constant.EXCHANGE_NAME_ONE, durable = "true", type = "topic"),
        value = @Queue(value = Constant.QUEUE_TOPIC_TEST_ONE, durable = "true"),
        key = Constant.ROUTING_KEY_ONE
))
public class TopicTestOneListener {

    @RabbitHandler
    public void topicTestOneListener(String str) {
        System.out.println("topicTestOneListener 已接收：" + str);
    }

}

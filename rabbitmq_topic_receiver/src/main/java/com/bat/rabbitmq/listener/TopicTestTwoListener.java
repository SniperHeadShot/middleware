package com.bat.rabbitmq.listener;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = Constant.EXCHANGE_NAME_TWO, durable = "true", type = "topic"),
        value = @Queue(value = Constant.QUEUE_TOPIC_TEST_TWO, durable = "true"),
        key = Constant.ROUTING_KEY_TWO
))
public class TopicTestTwoListener {

    @RabbitHandler
    public void topicTestTwoListener(String str) {
        System.out.println("topicTestTwoListener 已接收：" + str);
    }

}

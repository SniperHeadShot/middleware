package com.bat.rabbitmq.listener;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = Constant.EXCHANGE_NAME_ONE, durable = "true", type = "topic"),
        value = @Queue(value = Constant.QUEUE_TOPIC_TEST_ONE_SON, durable = "true"),
        key = Constant.ROUTING_KEY_ONE_SON
))
public class TopicTestOneSonListener {

    @RabbitHandler
    public void topicTestOneSonListener(String str) {
        System.out.println("topicTestOneSonListener 已接收：" + str);
    }

}

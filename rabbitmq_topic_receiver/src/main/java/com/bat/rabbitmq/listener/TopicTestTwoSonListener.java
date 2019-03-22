package com.bat.rabbitmq.listener;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = Constant.EXCHANGE_NAME_TWO, durable = "true", type = "topic"),
        value = @Queue(value = Constant.QUEUE_TOPIC_TEST_TWO_SON, durable = "true"),
        key = Constant.ROUTING_KEY_TWO_SON
))
public class TopicTestTwoSonListener {

    @RabbitHandler
    public void topicTestTwoSonListener(String str) {
        System.out.println("topicTestTwoSonListener 已接收：" + str);
    }

}

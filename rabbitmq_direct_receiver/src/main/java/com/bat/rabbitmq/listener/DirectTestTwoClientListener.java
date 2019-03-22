package com.bat.rabbitmq.listener;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = Constant.EXCHANGE_NAME_TWO,durable = "true",type = "direct"),
        value = @Queue(value = Constant.QUEUE_DIRECT_NAME_TWO,durable = "true"),
        key = Constant.ROUTING_KEY_TWO
))
public class DirectTestTwoClientListener {

    @RabbitHandler
    public void directTwoListener(String str) {
        System.out.println("DirectTestTwoClientListener 已接收：" + str);
    }
}
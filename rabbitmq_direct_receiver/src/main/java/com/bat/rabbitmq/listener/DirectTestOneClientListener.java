package com.bat.rabbitmq.listener;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = Constant.EXCHANGE_NAME_ONE,durable = "true",type = "direct"),
        value = @Queue(value = Constant.QUEUE_DIRECT_NAME_ONE,durable = "true"),
        key = Constant.ROUTING_KEY_ONE
))
public class DirectTestOneClientListener {

    @RabbitHandler
    public void directOneListener(String str) {
        System.out.println("DirectTestOneClientListener 已接收：" + str);
    }
}
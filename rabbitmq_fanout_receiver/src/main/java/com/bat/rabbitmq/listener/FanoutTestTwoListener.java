package com.bat.rabbitmq.listener;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = Constant.EXCHANGE_NAME, durable = "true", type = "fanout"),
        value = @Queue(value = Constant.QUEUE_TEST_TWO, durable = "true")
))
public class FanoutTestTwoListener {

    @RabbitHandler
    public void fanoutTestTwoListener(String str) {
        System.out.println("fanoutTestTwoListener 已接收：" + str);
    }

}

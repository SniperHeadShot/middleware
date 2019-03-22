package com.bat.rabbitmq.listener;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = Constant.EXCHANGE_NAME, durable = "true", type = "fanout"),
        value = @Queue(value = Constant.QUEUE_TEST_ONE, durable = "true")
))
public class FanoutTestOneListener {

    @RabbitHandler
    public void fanoutTestOneListener(String str) {
        System.out.println("fanoutTestOneListener 已接收：" + str);
    }

}

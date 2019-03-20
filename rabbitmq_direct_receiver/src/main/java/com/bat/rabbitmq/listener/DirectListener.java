package com.bat.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectListener {

    //Direct交换机生产的消息只能被一个队列处理，如果有多个接收者会逐个获取消息执行权限
    @RabbitListener(queues = "direct.test")
    public void directTestListener(String str) {
        System.out.println("已接收：" + str);
    }
}

package com.bat.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicListener {

    //Direct交换机生产的消息只能被一个队列处理，如果有多个接收者会逐个获取消息执行权限

    @RabbitListener(queues = "topic.one.test.one")
    public void topicOneTestOneListener(String str) {
        System.out.println("已接收：" + str);
    }

    @RabbitListener(queues = "topic.one.test.two")
    public void topicOneTestTwo(String str) {
        System.out.println("已接收：" + str);
    }

    @RabbitListener(queues = "topic.two.a")
    public void topicTwoA(String str) {
        System.out.println("已接收：" + str);
    }

    @RabbitListener(queues = "topic.two.b")
    public void topicTwoB(String str) {
        System.out.println("已接收：" + str);
    }
}

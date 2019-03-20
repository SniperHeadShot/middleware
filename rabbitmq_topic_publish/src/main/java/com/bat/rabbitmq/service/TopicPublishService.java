package com.bat.rabbitmq.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicPublishService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void topicOnePublish(){
        //第一个参数为交换机，第二个为队列名称,第三个为发送内容
        amqpTemplate.convertAndSend("exchangeTypeOne","topic.one","Hello Rabbitmq!");
    }

    public void topicTwoPublish(){
        //第一个参数为交换机，第二个为队列名称,第三个为发送内容
        amqpTemplate.convertAndSend("exchangeTypeTwo","topic.two","Hello Rabbitmq!");
    }
}

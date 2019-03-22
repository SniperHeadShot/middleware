package com.bat.rabbitmq.service;

import com.bat.rabbitmq.config.Constant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicPublishService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void topicOnePublish(){
        amqpTemplate.convertAndSend(Constant.EXCHANGE_NAME_ONE,Constant.ROUTING_KEY_ONE,"EXCHANGE_NAME_ONE ROUTING_KEY_ONE Hello Rabbitmq!");
    }

    public void topicOnePublishSon(){
        amqpTemplate.convertAndSend(Constant.EXCHANGE_NAME_ONE,Constant.ROUTING_KEY_ONE_SON,"EXCHANGE_NAME_ONE ROUTING_KEY_ONE_SON Hello Rabbitmq!");
    }

    public void topicTwoPublish(){
        amqpTemplate.convertAndSend(Constant.EXCHANGE_NAME_TWO,Constant.ROUTING_KEY_TWO,"EXCHANGE_NAME_TWO ROUTING_KEY_TWO Hello Rabbitmq!");
    }

    public void topicTwoPublishSon(){
        amqpTemplate.convertAndSend(Constant.EXCHANGE_NAME_TWO,Constant.ROUTING_KEY_TWO_SON,"EXCHANGE_NAME_TWO ROUTING_KEY_TWO_SON Hello Rabbitmq!");
    }
}

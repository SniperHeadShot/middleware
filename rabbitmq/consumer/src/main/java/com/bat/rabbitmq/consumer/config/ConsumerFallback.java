package com.bat.rabbitmq.consumer.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;

/**
 * TODO
 *
 * @author ZhengYu
 * @version 1.0 2020/7/9 2:13
 **/
public interface ConsumerFallback {

    void consumerMsg(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body);
}

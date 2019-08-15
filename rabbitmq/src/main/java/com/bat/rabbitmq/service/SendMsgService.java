package com.bat.rabbitmq.service;

import com.bat.common.enums.ConstantEnum;
import com.bat.common.response.CommonResult;
import com.bat.rabbitmq.enums.RabbitmqBaseConfigEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 *
 * @author ZhengYu
 * @version 1.0 2019/7/8 15:57
 **/
@Slf4j
@Service
public class SendMsgService {

    @Autowired
    private RabbitTemplate cloudRabbitTemplate;

    @Autowired
    private RabbitTemplate localRabbitTemplate;

    /**
     * 随机选择 Exchange, RoutingKey 发送消息
     *
     * @return com.bat.common.response.CommonResult
     * @author ZhengYu
     */
    public CommonResult sendMsg() {
        // 往云队列上发送消息
        cloudRabbitTemplate.convertAndSend(RabbitmqBaseConfigEnum.EXCHANGE_CLOUD_SCENES.getEnName(), RabbitmqBaseConfigEnum.TOPIC_CLOUD_SCENES.getEnName(), "hello cloud rabbitmq!");
        // 往本地队列上发送消息
        localRabbitTemplate.convertAndSend(RabbitmqBaseConfigEnum.EXCHANGE_LOCAL_SCENES.getEnName(), RabbitmqBaseConfigEnum.TOPIC_LOCAL_SCENES.getEnName(), "hello local rabbitmq!");
        // 往延迟缓冲队列上发送消息
        cloudRabbitTemplate.convertAndSend(RabbitmqBaseConfigEnum.EXCHANGE_CLOUD_SCENES.getEnName(), RabbitmqBaseConfigEnum.TOPIC_CLOUD_DELAY.getEnName(), "hello delay rabbitmq!", message -> {
            // 时间单位是毫秒
            message.getMessageProperties().setExpiration(Long.toString(TimeUnit.SECONDS.toMillis(3)));
            return message;
        });
        return CommonResult.buildCommonResult(ConstantEnum.GLOBAL_SUCCESS);
    }
}
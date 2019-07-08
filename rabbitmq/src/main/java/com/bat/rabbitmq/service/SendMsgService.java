package com.bat.rabbitmq.service;

import com.bat.common.enums.ConstantEnum;
import com.bat.common.response.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

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
        String cloudExchangeName = "exchange.cloud.scenes";
        String cloudRoutKeyName = "topic.cloud.scenes";
        log.info("即将使用 Exchange=[{}], RoutingKey=[{}] 发送消息", cloudExchangeName, cloudRoutKeyName);
        cloudRabbitTemplate.convertAndSend(cloudExchangeName, cloudRoutKeyName, "hello cloud rabbitmq!");

        String localExchangeName = "exchange.local.scenes";
        String localRoutKeyName = "topic.local.scenes";
        log.info("即将使用 Exchange=[{}], RoutingKey=[{}] 发送消息", localExchangeName, localRoutKeyName);
        localRabbitTemplate.convertAndSend(localExchangeName, localRoutKeyName, "hello local rabbitmq!");
        return CommonResult.buildCommonResult(ConstantEnum.GLOBAL_SUCCESS);
    }
}
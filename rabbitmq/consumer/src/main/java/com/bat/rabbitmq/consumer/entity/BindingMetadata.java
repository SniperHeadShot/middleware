package com.bat.rabbitmq.consumer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 绑定关系
 *
 * @author ZhengYu
 * @version 1.0 2020/7/9 1:29
 **/
@Data
@NoArgsConstructor
public class BindingMetadata {

    private String queueName;

    private String exchangeName;

    private String routingKey;

    public BindingMetadata(String queueName, String exchangeName, String routingKey) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }
}

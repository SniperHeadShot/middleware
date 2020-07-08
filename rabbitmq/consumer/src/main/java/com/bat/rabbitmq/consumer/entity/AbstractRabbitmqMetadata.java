package com.bat.rabbitmq.consumer.entity;

import lombok.Data;

import java.util.Map;

/**
 * Rabbitmq 信息
 *
 * @author ZhengYu
 * @version 1.0 2020/7/9 1:26
 **/
@Data
public class AbstractRabbitmqMetadata {

    private boolean durable;

    private boolean exclusive;

    private boolean autoDelete;

    private Map<String, Object> arguments;
}

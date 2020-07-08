package com.bat.rabbitmq.consumer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Rabbitmq 交换机
 *
 * @author ZhengYu
 * @version 1.0 2020/7/9 0:56
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExchangeMetadata extends AbstractRabbitmqMetadata {

    private String exchangeName;

    private String exchangeType = "topic";

    public ExchangeMetadata(String exchangeName) {
        this.exchangeName = exchangeName;
    }
}

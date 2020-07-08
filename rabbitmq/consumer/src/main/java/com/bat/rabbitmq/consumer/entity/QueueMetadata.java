package com.bat.rabbitmq.consumer.entity;

import com.bat.rabbitmq.consumer.config.ConsumerFallback;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rabbitmq 交换机
 *
 * @author ZhengYu
 * @version 1.0 2020/7/9 0:56
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QueueMetadata extends AbstractRabbitmqMetadata {

    private String queueName;

    private ConsumerFallback consumerFallback;

    private AtomicInteger referenceCounter = new AtomicInteger();

    public QueueMetadata(String queueName, ConsumerFallback consumerFallback) {
        this.queueName = queueName;
        this.consumerFallback = consumerFallback;
    }
}

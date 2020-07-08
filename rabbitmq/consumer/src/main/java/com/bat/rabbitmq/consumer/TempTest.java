package com.bat.rabbitmq.consumer;

import com.bat.rabbitmq.consumer.config.RabbitmqConnectionHolder;
import com.bat.rabbitmq.consumer.entity.BindingMetadata;
import com.bat.rabbitmq.consumer.entity.ExchangeMetadata;
import com.bat.rabbitmq.consumer.entity.QueueMetadata;

/**
 * TODO
 *
 * @author ZhengYu
 * @version 1.0 2020/4/2 15:46
 **/
public class TempTest {
    public static void main(String[] args) {
        RabbitmqConnectionHolder rabbitmqConnectionHolder = new RabbitmqConnectionHolder("47.100.114.192", 5672, "admin", "123456", "/");

        rabbitmqConnectionHolder.addExchangeMetadata(new ExchangeMetadata("test.exchange.20200709"));
        rabbitmqConnectionHolder.addQueueMetadata(new QueueMetadata("test.queue.20200709", (consumerTag, envelope, properties, body) -> {
            System.out.println(new String(body));
        }));
        rabbitmqConnectionHolder.addBindingMetadata(new BindingMetadata("test.queue.20200709", "test.exchange.20200709", "routingKey.exchange.20200709"));
        rabbitmqConnectionHolder.flushConsumer();
    }
}

package com.bat.rabbitmq.config;

public interface Constant {

    //Fanout类型交换机名称
    String EXCHANGE_NAME = "exchange.fanout.test";

    //Fanout队列名称
    String QUEUE_TEST_ONE = "queue.fanout.test.one";
    String QUEUE_TEST_TWO = "queue.fanout.test.two";
}
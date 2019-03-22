package com.bat.rabbitmq.config;

public interface Constant {

    //Direct类型匹配的RoutingKey
    String ROUTING_KEY_ONE = "direct.test.one";
    String ROUTING_KEY_TWO = "direct.test.two";

    //Direct类型交换机名称
    String EXCHANGE_NAME_ONE = "exchange.direct.test.one";
    String EXCHANGE_NAME_TWO = "exchange.direct.test.two";
}
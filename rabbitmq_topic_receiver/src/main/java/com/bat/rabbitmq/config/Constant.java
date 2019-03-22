package com.bat.rabbitmq.config;

public interface Constant {

    //Topic类型匹配的RoutingKey
    String ROUTING_KEY_ONE = "topic.test.one.#";
    String ROUTING_KEY_ONE_SON = "topic.test.one.son.#";
    String ROUTING_KEY_TWO = "topic.test.two.#";
    String ROUTING_KEY_TWO_SON = "topic.test.two.son.#";

    //Topic类型交换机名称
    String EXCHANGE_NAME_ONE = "exchange_topic_test_one";
    String EXCHANGE_NAME_TWO = "exchange_topic_test_two";

    //Topic类型队列名称
    String QUEUE_TOPIC_TEST_ONE = "queue.topic.test.one";
    String QUEUE_TOPIC_TEST_ONE_SON = "queue.topic.test.one.son";
    String QUEUE_TOPIC_TEST_TWO = "queue.topic.test.two";
    String QUEUE_TOPIC_TEST_TWO_SON = "queue.topic.test.two.son";
}
package com.bat.rabbitmq.enums;

/**
 * 普通消息队列配置枚举
 *
 * @author ZhengYu
 * @version 1.0 2019/8/13 16:44
 **/
public enum RabbitmqBaseConfigEnum {

    /**
     * 云环境配置枚举
     */
    EXCHANGE_CLOUD_SCENES("云环境 - 交换机", "exchange.cloud.scenes"),
    QUEUE_CLOUD_SCENES("云环境 - 队列", "queue.cloud.scenes"),
    TOPIC_CLOUD_SCENES("云环境 - 映射", "topic.cloud.scenes"),
    /**
     * 缓冲队列配置
     */
    QUEUE_CLOUD_DELAY("云环境 - 缓冲队列 - 发送到该队列的消息需要有过期时间", "queue.cloud.buffer"),
    TOPIC_CLOUD_DELAY("云环境 - 映射 - 发送到缓冲队列的消息携带的 RoutingKey", "topic.cloud.buffer"),
    /**
     * 死信队列配置
     */
    EXCHANGE_CLOUD_DLX("云环境 - 死信队列 - 交换机", "exchange.cloud.dlx"),
    /**
     * 本地环境配置枚举
     */
    EXCHANGE_LOCAL_SCENES("本地环境 - 交换机", "exchange.local.scenes"),
    QUEUE_LOCAL_SCENES("本地环境 - 队列", "queue.local.scenes"),
    TOPIC_LOCAL_SCENES("本地环境 - 映射", "topic.local.scenes");

    RabbitmqBaseConfigEnum(String name, String enName) {
        this.name = name;
        this.enName = enName;
    }

    private String name;

    private String enName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}

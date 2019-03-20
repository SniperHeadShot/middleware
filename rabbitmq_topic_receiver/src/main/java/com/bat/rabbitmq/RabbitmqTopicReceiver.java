package com.bat.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqTopicReceiver {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqTopicReceiver.class, args);
    }
}

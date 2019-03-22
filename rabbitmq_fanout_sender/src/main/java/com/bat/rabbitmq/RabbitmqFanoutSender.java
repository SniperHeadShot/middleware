package com.bat.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqFanoutSender {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqFanoutSender.class, args);
    }
}

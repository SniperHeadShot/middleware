package com.bat.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqFanoutReceiver {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqFanoutReceiver.class, args);
    }
}

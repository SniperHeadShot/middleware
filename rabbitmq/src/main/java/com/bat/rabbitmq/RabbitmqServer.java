package com.bat.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动主类
 *
 * @author ZhengYu
 * @version 1.0 2019/7/8 15:57
 **/
@SpringBootApplication
public class RabbitmqServer {
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqServer.class, args);
    }
}

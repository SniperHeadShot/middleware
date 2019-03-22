package com.bat.rabbitmq.testcontroller;

import com.bat.rabbitmq.service.DirectProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestProducerController {

    @Autowired
    private DirectProducerService directProducerService;

    @GetMapping("/testOne")
    public String testProduceOne() {
        try {
            directProducerService.sendOne();
        } catch (Exception e) {
            return "发送失败!!!";
        }
        return "发送成功!!!";
    }

    @GetMapping("/testTwo")
    public String testProduceTwo() {
        try {
            directProducerService.sendTwo();
        } catch (Exception e) {
            return "发送失败!!!";
        }
        return "发送成功!!!";
    }
}

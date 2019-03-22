package com.bat.rabbitmq.testcontroller;

import com.bat.rabbitmq.service.FanoutSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSenderController {

    @Autowired
    private FanoutSenderService fanoutSenderService;

    @GetMapping("test")
    public String testSend() {
        fanoutSenderService.fanoutSendMes();
        try {
            return "发送成功!!!";
        } catch (Exception e) {
            return "发送失败!!!";
        }
    }
}

package com.bat.rabbitmq.testcontroller;

import com.bat.rabbitmq.service.TopicPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestPublishController {

    @Autowired
    private TopicPublishService topicPublishService;

    @GetMapping("/testOne")
    public String testTopicTestOne() {
        try {
            topicPublishService.topicOnePublish();
            return "发送成功!!!";
        } catch (Exception e) {
            return "发送失败!!!";
        }
    }

    @GetMapping("/testOneSon")
    public String testTopicTestOneSon() {
        try {
            topicPublishService.topicOnePublishSon();
            return "发送成功!!!";
        } catch (Exception e) {
            return "发送失败!!!";
        }
    }

    @GetMapping("/testTwo")
    public String testTopicTestTwo() {
        try {
            topicPublishService.topicTwoPublish();
            return "发送成功!!!";
        } catch (Exception e) {
            return "发送失败!!!";
        }
    }

    @GetMapping("/testTwoSon")
    public String testTopicTestTwoSon() {
        try {
            topicPublishService.topicTwoPublishSon();
            return "发送成功!!!";
        } catch (Exception e) {
            return "发送失败!!!";
        }
    }
}

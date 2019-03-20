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
    public void testTopicTestOne(){
        topicPublishService.topicOnePublish();
    }

    @GetMapping("/testTwo")
    public void testTopicTestTwo(){
        topicPublishService.topicTwoPublish();
    }
}

package com.bat.rabbitmq.testcontroller;

import com.bat.rabbitmq.service.DirectProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestProducerController {

    @Autowired
    private DirectProducerService directProducerService;

    @GetMapping("/test")
    public void testProduce(){
        directProducerService.send();
    }
}

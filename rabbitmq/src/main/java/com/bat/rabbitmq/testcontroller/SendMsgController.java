package com.bat.rabbitmq.testcontroller;

import com.bat.common.response.CommonResult;
import com.bat.rabbitmq.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制层
 *
 * @author ZhengYu
 * @version 1.0 2019/7/8 15:57
 **/
@RestController
public class SendMsgController {

    @Autowired
    private SendMsgService sendMsgService;

    /**
     * 发送普通消息
     *
     * @return com.bat.common.response.CommonResult
     * @author ZhengYu
     */
    @GetMapping("/send")
    public CommonResult sendMsg() {
        return sendMsgService.sendMsg();
    }
}

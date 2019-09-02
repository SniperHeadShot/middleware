package com.bat.rabbitmq.nativemode;

import lombok.Data;

/**
 * 原生方式监听
 *
 * @author ZhengYu
 * @version 1.0 2019/8/31 13:45
 **/
@Data
class NativeRabbitmqConfig {

    private String host;

    private String port;

    private String username;

    private String password;

    private String virtualHost;
}

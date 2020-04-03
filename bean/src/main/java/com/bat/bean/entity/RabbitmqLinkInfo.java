package com.bat.bean.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rabbitmq 连接信息
 *
 * @author ZhengYu
 * @version 1.0 2020/4/1 18:52
 **/
@Data
@NoArgsConstructor
public class RabbitmqLinkInfo {

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String virtualHost;

    public RabbitmqLinkInfo(String host, Integer port, String username, String password, String virtualHost) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.virtualHost = virtualHost;
    }
}

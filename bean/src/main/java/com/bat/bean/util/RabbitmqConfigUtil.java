package com.bat.bean.util;

import com.bat.bean.entity.RabbitmqLinkInfo;

/**
 * RabbitmqConfig 获取连接信息工具类
 *
 * @author ZhengYu
 * @version 1.0 2020/4/1 19:01
 **/
public class RabbitmqConfigUtil {

    public static RabbitmqLinkInfo getCloudRabbitmqLinkInfo() {
        return new RabbitmqLinkInfo("47.100.114.192", 5672, "admin", "123456", "/");
    }
}

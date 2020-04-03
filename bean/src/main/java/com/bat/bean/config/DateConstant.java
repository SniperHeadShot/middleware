package com.bat.bean.config;

import java.time.format.DateTimeFormatter;

/**
 * 时间格式
 *
 * @author ZhengYu
 * @version 1.0 2020/4/2 14:03
 **/
public class DateConstant {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}

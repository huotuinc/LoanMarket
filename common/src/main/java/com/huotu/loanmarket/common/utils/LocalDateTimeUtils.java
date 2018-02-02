/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017. All rights reserved.
 */

package com.huotu.loanmarket.common.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author luyuanyuan on 2017/11/7.
 */
public class LocalDateTimeUtils {


    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static final DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");


    /**
     * 字符串转换成LocalDateTime
     *
     * @param text yyyy-MM-dd hh:mm:ss
     * @return
     * @throws ParseException 转换异常
     */
    public static LocalDateTime toLocalDateTime(String text){
        if (StringUtils.isEmpty(text))
            return null;

        return LocalDateTime.from(dateTimeFormatter.parse(text));
    }

    /**
     * 日期转换为字符串
     *
     * @param dateTime
     * @return yyyy-MM-dd hh:mm:ss 格式字符
     */
    public static String toStr(LocalDateTime dateTime) {
        if(dateTime==null){
            return "";
        }
        return dateTimeFormatter.format(dateTime);
    }

    /**
     * 日期转换为字符串
     *
     * @param dateTime
     * @return yyyyMMddhhmmssSSS 格式字符
     */
    public static String toStr2(LocalDateTime dateTime) {
        return dateTimeFormatter2.format(dateTime);
    }

}

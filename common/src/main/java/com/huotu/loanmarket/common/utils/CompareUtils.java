/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.common.utils;

import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * 比较工具类
 *
 * @author guomw
 * @date 15/11/2017
 */
public class CompareUtils {


    /**
     * 判断time是否只小于当前时间的秒
     *
     * @param time    指定时间
     * @param seconds 秒
     * @return
     */
    public static boolean compareToDate(LocalDateTime time, long seconds) {
        LocalDateTime curDate = LocalDateTime.now().minusSeconds(seconds);
        return time.isAfter(curDate);
    }


    /**
     * 判断timestamp是否在指定的时间范围内
     *
     * @param timestamp 时间戳
     * @param seconds   指定时间
     */
    public static boolean compareToTimestamp(long timestamp, long seconds) throws ParseException {
        //最大时间
        long curDate = System.currentTimeMillis();
        //最小时间
        long minCurDate = curDate - seconds * 1000;
        if (timestamp >= minCurDate && timestamp <= (curDate + 60 * 1000)) {
            return true;
        }
        return false;
    }
}

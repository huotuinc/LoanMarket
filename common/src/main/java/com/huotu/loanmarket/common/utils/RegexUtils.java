/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guomw
 * @date 13/11/2017
 */
public class RegexUtils {


    /**
     * 正则校验
     * @param str
     * @param regex
     * @return
     */
    public static boolean check(String str, String regex) {
        boolean flag;
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 校验手机号码格式
     * 格式为1[3-9]********* 11位数字
     * @param mobile
     * @return
     */
    public static boolean checkMobile(String mobile){
        String regex = "^(1[3-9])\\d{9}$";
        return check(mobile, regex);
    }

    /**
     * 验证是否是数字格式
     * @param input
     * @return
     */
    public  static boolean checkNumber(String input){
        String regex="^\\d+$";
        return check(input, regex);
    }
    /**
     * 验证是否是日期格式
     * @param s
     * @return
     */
    public static boolean validateIsDate(String s) {
        return !StringUtils.isEmpty(s) && Pattern.matches("^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$", s);
    }

    /**
     * 验证是否是非负浮点数+0，匹配如：
     * 1.333
     * 1.3
     * 100
     * 0
     * @param s
     * @return
     */
    public static boolean checkNonnegativeFloat(String s){
        return  check(s,"^\\d+(\\.\\d+)?$");
    }

    /**
     * 验证是否是正浮点数
     * @param s
     * @return
     */
    public static boolean checkPositiveFloat(String s){
        return check(s,"^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
    }
}

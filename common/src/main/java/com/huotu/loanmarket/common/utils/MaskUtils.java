package com.huotu.loanmarket.common.utils;

import org.springframework.util.StringUtils;

/**
 * 打码助手
 * @author wm
 */
public class MaskUtils {
    /**
     * 手机号打码
     * @param mobile
     * @return
     */
    public static String maskMobile(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{5}(\\d{3})", "$1*****$2");
    }

    /**
     * 姓名打码
     * @param realName
     * @return
     */
    public static String maskRealName(String realName) {
        if(StringUtils.isEmpty(realName)){
            return "";
        }
        if (realName.length() <= 1) {
            return "*";
        } else {
            return realName.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1" + createAsterisk(realName.length() - 1));
        }
    }

    /**
     * 姓名打码
     * @param realName
     * @param suffix
     * @return
     */
    public static String maskRealName(String realName,String suffix) {
        if(StringUtils.isEmpty(realName)){
            return "";
        }
        if (realName.length() <= 1) {
            return "*";
        } else {
            return realName.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1" + suffix);
        }
    }

    /**
     * 文字截断打码
     * @param title
     * @param length
     * @return
     */
    public static String maskTitle(String title,int length){
        return maskTitle(title,length,"******");
    }

    /**
     * 文字截断打码
     * @param title
     * @param length
     * @param suffix
     * @return
     */
    public static String maskTitle(String title,int length,String suffix){
        if(StringUtils.isEmpty(title)){
            return "";
        }
        if(title.length()<length){
            return title;
        }else{
            return title.substring(0,length-1)+suffix;
        }
    }

    /**
     * 身份证号打码
     * @param idNumber
     * @return
     */
    public static String maskIdNumber(String idNumber) {
        if(StringUtils.isEmpty(idNumber)){
            return "";
        }
        if (idNumber.length() == 15) {
            return idNumber.replaceAll("(\\d{3})\\d{9}(\\w{3})", "$1*********$2");
        } else if (idNumber.length() == 18) {
            return idNumber.replaceAll("(\\d{3})\\d{12}(\\w{3})", "$1************$2");
        }
        return idNumber;
    }

    private static String createAsterisk(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }
}
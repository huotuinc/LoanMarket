/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.common.utils;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * 获取request数据
 *
 * @author guomw
 * @date 2017/12/18
 */
@Data
public class RequestUtils {

    private static final Log log = LogFactory.getLog(RequestUtils.class);

    /**
     * 根据指定key获取header数据
     *
     * @param request
     * @param key
     * @return
     */
    public static String getHeader(HttpServletRequest request, String key) {
        String result = "";
        if (containsHeaderKey(request, key)) {
            try {
                return URLDecoder.decode(request.getHeader(key), "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
                return result;
            }
        }
        return result;
    }

    /**
     * 根据指定key获取header数据
     * @param request
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String getHeader(HttpServletRequest request, String key,String defaultValue) {
        String result = getHeader(request,key);
        if (StringUtils.isEmpty(result)) {
            result = defaultValue;
        }
        return result;
    }
    /**
     * 根据指定key获取header数据
     *
     * @param request
     * @param key
     * @return
     */
    public static int getIntHeader(HttpServletRequest request, String key) {
        int result = 0;
        if (containsHeaderKey(request, key)) {
            return request.getIntHeader(key);
        }
        return result;
    }

    /**
     * 根据指定key获取请求数据
     *
     * @param request
     * @param key
     * @return
     */
    public static String getParameter(HttpServletRequest request, String key) {
        String result = "";
        if (containsKey(request, key)) {
            try {
                return URLDecoder.decode(request.getParameter(key), "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
                return result;
            }
        }
        return result;
    }

    public static Long getLongParameter(HttpServletRequest request, String key) {
        String value = getParameter(request, key);
        if (StringUtils.isEmpty(value)) {
            return null;
        } else {
            return Long.valueOf(value);
        }
    }


    /**
     * 判断header key是否存在
     *
     * @param request
     * @param key
     * @return
     */
    public static boolean containsHeaderKey(HttpServletRequest request, String key) {
        return contains(request.getHeaderNames(), key);
    }

    /**
     * 判断Parameter key是否存在
     *
     * @param request
     * @param key
     * @return
     */
    public static boolean containsKey(HttpServletRequest request, String key) {
        return contains(request.getParameterNames(), key);
    }


    /**
     * 判断key是否存在
     *
     * @param keys
     * @param key
     * @return
     */
    private static boolean contains(Enumeration<String> keys, String key) {
        boolean result = false;
        while (keys.hasMoreElements()) {
            if (key.toLowerCase().equals(keys.nextElement().toLowerCase())) {
                result = true;
                break;
            }
        }
        return result;
    }

}

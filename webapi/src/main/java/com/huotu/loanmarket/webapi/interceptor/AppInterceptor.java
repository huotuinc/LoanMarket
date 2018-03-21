/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2017. All rights reserved.
 */


package com.huotu.loanmarket.webapi.interceptor;

import com.alibaba.fastjson.JSON;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.BuildSignUtils;
import com.huotu.loanmarket.common.utils.CompareUtils;
import com.huotu.loanmarket.common.utils.RequestUtils;
import com.huotu.loanmarket.service.enums.AppCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.*;

/**
 * 调用API 时需要对请求参数进行签名验证(header不加入签名，但不包含[merchantId,userId,userToken,cityname,citycode])，
 * 先将参数名称全部转换成小写，
 * 然后根据转成小写后的参数名称按照字母先后顺序排序:key + value .... key + value，
 * 其中加入上述除了sign以外的系统级参数，然后将secretKey拼接到尾部然后MD5加密所得
 * 例如：将appId=1,token=2,timestamp=1232,someData1=””,someData2=”123”
 * 进行排序后所得字符串appid1somedata2123timestamp1232token2
 * 然后加入secretKey=ABCD拼接于尾部，最后得到：appid1timestamp1232token2ABCD
 *
 * @author guomw
 * @date 29/01/2018
 */
public class AppInterceptor extends HandlerInterceptorAdapter {
    private static Log log = LogFactory.getLog(AppInterceptor.class);

    @Autowired
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        if (env.acceptsProfiles(Constant.PROFILE_DEV, Constant.PROFILE_UN_CHECK)) {
            return true;
        }

        /**
         * 检查 appVersion,osType 几个参数是否没传，没传指的是没有这个key
         */
        if (!checkMustParameter(request, response)) {
            return false;
        }
        Long timestamp = RequestUtils.getLongParameter(request, Constant.APP_TIMESTAMP_KEY);
        /**
         * 验证时间戳是否是在有效时间内
         */
        if (timestamp == null || !CompareUtils.compareToTimestamp(timestamp, Constant.TIMESTAMP_VALID_TIME_LENGTH)) {
            response.getWriter().write(JSON.toJSONString(ApiResult.resultWith(AppCode.TIMESTAMP_ERROR)));
            return false;
        }

        String requestSign = RequestUtils.getParameter(request, Constant.SIGN_KEY);
        //签名未传
        if (StringUtils.isEmpty(requestSign)) {
            response.getWriter().write(JSON.toJSONString(ApiResult.resultWith(AppCode.SIGN_NOT_PASS)));
            return false;
        }

        if (!requestSign.equalsIgnoreCase(getSign(request))) {
            response.getWriter().write(JSON.toJSONString(ApiResult.resultWith(AppCode.SIGN_ERROR)));
            return false;
        }


        return true;
    }


    /**
     * 检查必要的参数是否存
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private boolean checkMustParameter(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         * 检查 appVersion,osType 几个参数是否没传，没传指的是没有这个key
         */
        boolean result = true;
        StringBuffer sb = new StringBuffer();
        if (!RequestUtils.containsHeaderKey(request, Constant.APP_VERSION_KEY)) {
            sb.append(Constant.APP_VERSION_KEY);
            result = false;
        }
        if (!RequestUtils.containsHeaderKey(request, Constant.APP_SYSTEM_TYPE_KEY)) {
            sb.append((result ? "" : ",") + Constant.APP_SYSTEM_TYPE_KEY);
            result = false;
        }
        if (sb.length() > 0) {
            String errorMsg = MessageFormat.format("缺少{0}必传参数", sb.toString());
            response.getWriter().write(JSON.toJSONString(ApiResult.resultWith(AppCode.PARAMETER_ERROR, errorMsg)));
        }

        return result;
    }

    /**
     * 获取签名
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getSign(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> resultMap = new TreeMap<>();

        /**
         * header参数【merchantId,userId,userToken,cityname,citycode】参与签名
         */
        String merchantId =Constant.MERCHANT_ID.toString();
        String userId = RequestUtils.getHeader(request, Constant.APP_USER_ID_KEY);
        String userToken = RequestUtils.getHeader(request, Constant.APP_USER_TOKEN_KEY);
        String cityName = RequestUtils.getHeader(request, Constant.APP_CITY_NAME_KEY);
        String cityCode = RequestUtils.getHeader(request, Constant.APP_CITY_CODE_KEY);
        /**
         * 将参数加入签名对象中
         */
        resultMap.put(Constant.APP_MERCHANT_ID_KEY.toLowerCase(), merchantId);
        resultMap.put(Constant.APP_USER_ID_KEY.toLowerCase(), userId);
        resultMap.put(Constant.APP_USER_TOKEN_KEY.toLowerCase(), userToken);
        resultMap.put(Constant.APP_CITY_NAME_KEY.toLowerCase(), cityName);
        resultMap.put(Constant.APP_CITY_CODE_KEY.toLowerCase(), cityCode);

        Map<String, String[]> map = request.getParameterMap();
        map.forEach((key, value) -> {
            if (!Constant.SIGN_KEY.equals(key)) {
                if (value != null && value.length > 0) {
                    resultMap.put(key.toLowerCase(), value[0]);
                }
            }
        });
        String strSign = BuildSignUtils.buildSignIgnoreEmpty(resultMap, null, Constant.SECRET_KEY);
        log.debug("sign:" + strSign);
        return strSign.toUpperCase();
    }
}

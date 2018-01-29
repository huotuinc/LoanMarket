/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2017. All rights reserved.
 */


package com.huotu.loanmarket.webapi.interceptor;

import com.huotu.loanmarket.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        if (env.acceptsProfiles(Constant.PROFILE_DEV, Constant.PROFILE_UN_CHECK)) {
            return true;
        }


        return true;
    }


}

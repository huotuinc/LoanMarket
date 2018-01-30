/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.webapi.interceptor;

import com.alibaba.fastjson.JSON;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.RequestUtils;
import com.huotu.loanmarket.service.enums.AppCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录校验拦截器
 * @author guomw
 * @date 2017/12/28
 */
public class AppLoginInterceptor extends HandlerInterceptorAdapter {

//    @Autowired
//    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        int merchantId = Constant.MERCHANT_ID;
        String token = RequestUtils.getHeader(request, Constant.APP_USER_TOKEN_KEY);
        long userId = RequestUtils.getIntHeader(request, Constant.APP_USER_ID_KEY);
        /**
         * 如果token和userid都不为空，则去判断登录状态
         */
//        if (!userService.checkLoginToken(merchantId, userId, token)) {
//            if (userId > 0 || !StringUtils.isEmpty(token)) {
//                response.getWriter().write(JSON.toJSONString(ApiResult.resultWith(AppCode.TOKEN_ERROR,"你的账号已在另一台设备登录。如非本人操作，则密码可能已泄露，建议修改密码。")));
//            }
//            else {
//                response.getWriter().write(JSON.toJSONString(ApiResult.resultWith(AppCode.TOKEN_ERROR)));
//            }
//            return false;
//        }
        return true;
    }

}

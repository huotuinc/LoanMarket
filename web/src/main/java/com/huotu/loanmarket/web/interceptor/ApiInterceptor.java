package com.huotu.loanmarket.web.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进行签名校验，/rest/api/**的需要拦截
 * 只需验证heard中是否有userId，并判断是否存在
 *
 * @author allan
 * @date 26/10/2017
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        long userId = request.getIntHeader("userId");

        return true;
    }
}

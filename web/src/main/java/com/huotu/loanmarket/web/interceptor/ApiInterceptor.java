package com.huotu.loanmarket.web.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进行签名校验，/rest/api/**的需要拦截
 *
 * @author allan
 * @date 26/10/2017
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO: 26/10/2017
        return super.preHandle(request, response, handler);
    }
}

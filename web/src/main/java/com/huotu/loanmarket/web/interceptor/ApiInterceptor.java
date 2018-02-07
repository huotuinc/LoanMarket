package com.huotu.loanmarket.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.service.UserService;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进行校验，/forend/project/**的需要拦截
 *
 *
 * @author allan
 * @date 26/10/2017
 */
@Component
public class ApiInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        String userId = request.getParameter("userId");
//        if (StringUtils.isEmpty(userId)) {
//            response.getWriter().write(new ObjectMapper().writeValueAsString("用户编号未传"));
//            return false;
//        }
//        LoanUser user = userService.findOne(Long.parseLong(userId));
//        if (user == null) {
//            response.getWriter().write(new ObjectMapper().writeValueAsString("用户不存在"));
//            return false;
//        }
        return true;
    }
}

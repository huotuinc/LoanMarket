package com.huotu.loanmarket.web.controller.filter;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hxh on 2017-10-26.
 */
@Component
public class ParamFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Map<String,String[]> m = new HashMap<String,String[]>(request.getParameterMap());
        request = new ParameterRequestWrapper((HttpServletRequest)request, m);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

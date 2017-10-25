package com.huotu.loanmarket.web.config.security;

import lombok.Setter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by allan on 25/10/2017.
 */
@Setter
public class MultipleAuthenticationLoginEntry implements AuthenticationEntryPoint {
    private String defaultLoginUrl;
    private List<RequestUriDirectUrlResolver> requestUriDirectUrlResolvers;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        for (RequestUriDirectUrlResolver requestUriDirectUrlResolver : requestUriDirectUrlResolvers) {
            if (requestUriDirectUrlResolver.support(request)) {
                response.sendRedirect(requestUriDirectUrlResolver.getDirectUrl());
                return;
            }
        }
        response.sendRedirect(defaultLoginUrl);
    }
}

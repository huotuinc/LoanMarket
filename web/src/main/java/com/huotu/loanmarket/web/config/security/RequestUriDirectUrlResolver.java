package com.huotu.loanmarket.web.config.security;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author allan
 * @date 25/10/2017
 */
@Setter
@Getter
public class RequestUriDirectUrlResolver {
    private String pattern;
    private String directUrl;

    public boolean support(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();

        return requestUrl.contains(this.pattern);
    }
}

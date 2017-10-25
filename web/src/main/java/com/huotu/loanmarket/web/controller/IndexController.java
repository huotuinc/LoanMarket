package com.huotu.loanmarket.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author allan
 * @date 19/10/2017
 */
@Controller
public class IndexController {
    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    @PreAuthorize("hasRole('ADMIN')")
    public String index() {
        return "index";
    }
}

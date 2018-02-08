package com.huotu.loanmarket.web.controller.sesame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/sesame")
@Controller
public class SesameController {
    @RequestMapping("/getReport")
    public String getSesameReport() {
        return null;
    }
}

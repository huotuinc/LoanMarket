package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.web.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author allan
 * @date 25/10/2017
 */
@Controller
public class BaseController {
    @Autowired
    private StaticResourceService resourceService;

    @ModelAttribute(value = "resourceService")
    public StaticResourceService resourceService() {
        return resourceService;
    }
}

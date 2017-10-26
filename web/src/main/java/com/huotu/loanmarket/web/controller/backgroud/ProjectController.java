package com.huotu.loanmarket.web.controller.backgroud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author allan
 * @date 26/10/2017
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(
            @RequestParam(required = false, defaultValue = "0") int projectId
    ) {
        return "eidt_project";
    }
}

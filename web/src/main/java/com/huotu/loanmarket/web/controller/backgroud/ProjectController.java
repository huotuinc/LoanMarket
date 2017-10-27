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
@RequestMapping("/backend/project")
public class ProjectController {

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(
            @RequestParam(required = false, defaultValue = "0") int projectId
    ) {

        return "edit_project";
    }
}

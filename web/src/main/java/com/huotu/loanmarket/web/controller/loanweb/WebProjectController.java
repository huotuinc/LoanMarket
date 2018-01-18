package com.huotu.loanmarket.web.controller.loanweb;

import com.huotu.loanmarket.common.ienum.ApplicationMaterialEnum;
import com.huotu.loanmarket.common.ienum.EnumHelper;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import com.huotu.loanmarket.web.service.StaticResourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxh
 * @date 2017-10-31
 */
@Controller
@RequestMapping("/forend/project")
public class WebProjectController {
    private static final Log log = LogFactory.getLog(WebProjectController.class);
    @Autowired
    private ProjectService projectService;
    @Autowired
    private StaticResourceService staticResourceService;

    @RequestMapping("/search/list")
    @ResponseBody
    public ApiResult getProjectList(int categoryId, double amount, int deadline) {
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, projectService.findAll(categoryId, amount, deadline));
    }

    @RequestMapping("/list")
    public String getProjectList(Long userId, Model model) {
        List<LoanProject> loanprojectlist = projectService.findAll();
        loanprojectlist.forEach(p -> {
            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    p.setLogo(staticResourceService.get(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                    log.error("获取图片异常：" + e);
                }
            }
        });
        model.addAttribute("projectList", loanprojectlist);
        model.addAttribute("userId", userId);
        return "forend/loanList";
    }

    @RequestMapping("/getDetail")
    public String getProjectDetail(int projectId, Model model) {
        LoanProject loanProject = projectService.findOne(projectId);
        if (!StringUtils.isEmpty(loanProject.getDeadline())) {
            String[] deadline = loanProject.getDeadline().split(",");
            loanProject.setMinDeadline(Integer.parseInt(deadline[0]));
            loanProject.setMaxDeadline(Integer.parseInt(deadline[deadline.length - 1]));
        }
        if (!StringUtils.isEmpty(loanProject.getEnableMoney())) {
            String[] enableMoney = loanProject.getEnableMoney().split(",");
            loanProject.setMinMoney(Double.parseDouble(enableMoney[0]));
            loanProject.setMaxMoney(Double.parseDouble(enableMoney[enableMoney.length - 1]));
        }
        List<ApplicationMaterialEnum> materialEnumList = new ArrayList<>();
        if (!StringUtils.isEmpty((loanProject.getApplicationMaterial()))) {
            String[] materials = loanProject.getApplicationMaterial().split(",");
            for (String material : materials) {
                ApplicationMaterialEnum enumType = EnumHelper.getEnumType(ApplicationMaterialEnum.class, Integer.parseInt(material));
                materialEnumList.add(enumType);
            }
        }
        if (!StringUtils.isEmpty(loanProject.getLogo())) {
            try {
                loanProject.setLogo(staticResourceService.get(loanProject.getLogo()).toString());
            } catch (URISyntaxException e) {
                log.error("获取图片异常：" + e);
            }
        }
        if (!StringUtils.isEmpty(loanProject.getTag()) && loanProject.getTag().split(",").length > 3) {
            String[] tags = loanProject.getTag().split(",");
            String tag = tags[0] + "," + tags[1] + "," + tags[2];
            loanProject.setTag(tag);
        }
        model.addAttribute("materials", materialEnumList);
        model.addAttribute("loanProject", loanProject);
        return "forend/loanDetail";
    }

    @RequestMapping("/loanProcess")
    public String loanProcess(Model model) {
        List<LoanProject> hotProject = projectService.getHotProject();
        hotProject.forEach(p -> {
            if (!StringUtils.isEmpty(p.getLogo())) {
                try {
                    p.setLogo(staticResourceService.get(p.getLogo()).toString());
                } catch (URISyntaxException e) {
                    log.error("获取图片异常：" + e);
                }
            }
        });
        model.addAttribute("hotProject", hotProject);
        return "forend/loanProgress";
    }
}

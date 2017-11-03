package com.huotu.loanmarket.web.controller.loanweb;

import com.huotu.loanmarket.common.ienum.ApplicationMaterialEnum;
import com.huotu.loanmarket.common.ienum.EnumHelper;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxh
 * @date 2017-10-31
 */
@Controller
@RequestMapping("/forend/project")
public class WebProjectController {
    @Autowired
    private ProjectService projectService;

    @RequestMapping("/search/list")
    @ResponseBody
    public ApiResult getProjectList(int categoryId, double amount, int deadline){
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS,projectService.findAll(categoryId, amount, deadline));
    }
    @RequestMapping("/list")
    public String getProjectList(){
        return "forend/loanList";
    }
    @RequestMapping("/getDetail")
    public String getProjectDetail(int projectId, Model model) {
        LoanProject loanProject = projectService.findOne(projectId);
        String[] deadline = loanProject.getDeadline().split(",");
        String[] enableMoney = loanProject.getEnableMoney().split(",");
        String[] materials = loanProject.getApplicationMaterial().split(",");
        List<ApplicationMaterialEnum> materialEnumList = new ArrayList<>();
        for (int i = 0; i < materials.length; i++) {
            ApplicationMaterialEnum enumType = EnumHelper.getEnumType(ApplicationMaterialEnum.class, Integer.parseInt(materials[i]));
            materialEnumList.add(enumType);
        }
        model.addAttribute("minDeadline", deadline[0]);
        model.addAttribute("maxDeadline", deadline[deadline.length - 1]);
        model.addAttribute("minMoney", enableMoney[0]);
        model.addAttribute("maxMoney", enableMoney[enableMoney.length - 1]);
        model.addAttribute("materials", materialEnumList);
        model.addAttribute("loanProject", loanProject);
        return "forend/loanDetail";
    }

    @RequestMapping("/loanProcess")
    public String loanProcess(Model model) {
        List<LoanProject> hotProject = projectService.getHotProject();
        model.addAttribute("hotProject", hotProject);
        return "forend/loanProgress";
    }
    @RequestMapping("/apply")
    @ResponseBody
    public void apply(){
        /**从cookie中获取购物车*/
//        try {
//            String cartsStr = CookieHelper.getCookieVal(request, "jd_cart_" + customId);
//            if (!StringUtils.isEmpty(cartsStr)) {
//                carts = URLDecoder.decode(cartsStr, "utf-8");
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }
}

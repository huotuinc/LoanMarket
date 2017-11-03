/*
 *   ~ 版权所有:杭州火图科技有限公司
 *   ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *   ~
 *   ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *   ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *   ~ 2017-2020. All rights reserved.
 */

package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.service.CategoryService;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;


/**
 * @author allan
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping("/list")
    public String list(Model model) {
        List<LoanCategory> items = categoryService.findAll();
        model.addAttribute("items", items);
        return "category_list";
    }


    /**
     * 编辑分类
     *
     * @param categoryId
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(
            @RequestParam(required = false, defaultValue = "0") Integer categoryId,
            Model model
    ) {

        LoanCategory category;
        if (categoryId == 0) {
            category = new LoanCategory();
        } else {
            category = categoryService.findOne(categoryId);
        }
        model.addAttribute("category", category);

        List<LoanCategory> items = categoryService.findAll();

        items.removeIf(item -> item.getCategoryId().equals(categoryId));


        model.addAttribute("items", items);
        return "category_edit";
    }


    /**
     * 保存分类数据
     *
     * @param categoryId
     * @param categoryName
     * @param categoryIcon
     * @param categoryParentId
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult save(@RequestParam(required = false, defaultValue = "0") int categoryId,
                          @RequestParam String categoryName,
                          @RequestParam String categoryIcon,
                          @RequestParam(required = false, defaultValue = "0") String categoryParentId
    ) {

        LoanCategory category;

        if (categoryId > 0) {
            category = categoryService.findOne(categoryId);
        } else {
            category = new LoanCategory();
        }
        category.setName(categoryName);
        category.setIcon(categoryIcon);
        category.setParentId(categoryParentId);
        category = categoryService.save(category);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, category);
    }

    /**
     * 删除分类，只是简单的删除当前分类，为做关联删除逻辑
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult delete(@RequestParam Integer categoryId) {

        List<LoanProject> projectList = projectService.findAll();

        boolean flag = false;

        for (LoanProject p :
                projectList) {
            flag = p.getCategories().contains("," + categoryId + ",");
            if (flag) {
                break;
            }
        }

        if (!flag) {
            categoryService.delete(categoryId);
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        } else {
            return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST, "删除失败，该分类已被使用", null);
        }
    }
}

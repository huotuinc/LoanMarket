package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.service.CategoryService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/backend/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

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
    public ApiResult delete(@RequestParam int categoryId) {

        categoryService.delete(categoryId);

        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }
}

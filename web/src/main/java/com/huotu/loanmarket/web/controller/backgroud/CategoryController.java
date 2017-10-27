package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backend/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public String list(){
        return "category_list";
    }


    @RequestMapping("/edit")
    public  String edit(){


        return "category_edit";
    }

}

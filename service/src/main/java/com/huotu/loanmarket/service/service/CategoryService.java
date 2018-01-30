package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.Category.Category;

import java.util.List;

public interface CategoryService extends CrudService<Category,Integer>{


    List<Category> findAll();

}

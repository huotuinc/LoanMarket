package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.Category.Category;
import com.huotu.loanmarket.service.repository.category.CategoryRepository;
import com.huotu.loanmarket.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends AbstractCrudService<Category,Integer> implements CategoryService {

    CategoryRepository loanCategoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository){
        super(repository);
        this.loanCategoryRepository = repository;
    }

    @Override
    public List<Category> findAll() {
        return loanCategoryRepository.findAll();
    }
}

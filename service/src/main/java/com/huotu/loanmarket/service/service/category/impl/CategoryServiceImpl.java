package com.huotu.loanmarket.service.service.category.impl;

import com.huotu.loanmarket.service.entity.category.Category;
import com.huotu.loanmarket.service.repository.category.CategoryRepository;
import com.huotu.loanmarket.service.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findOne(int categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(int categoryId) {
        categoryRepository.delete(categoryId);
    }
}

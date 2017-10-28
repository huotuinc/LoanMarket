package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.repository.LoanCategoryRepository;
import com.huotu.loanmarket.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class CategoryServiceImpl extends AbstractCrudService<LoanCategory, Integer> implements CategoryService {
    private LoanCategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(LoanCategoryRepository repository) {
        super(repository);
        categoryRepository = repository;
    }

    @Override
    public List<LoanCategory> getAll() {
        return this.repository.findAll();
    }
}

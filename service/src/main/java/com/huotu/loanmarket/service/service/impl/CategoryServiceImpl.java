package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.repository.LoanCategoryRepository;
import com.huotu.loanmarket.service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends AbstractCrudService<LoanCategory,Integer> implements CategoryService {

    @Autowired
    LoanCategoryRepository loanCategoryRepository;

    @Autowired
    public CategoryServiceImpl(LoanCategoryRepository repository){
        super(repository);
    }

    @Override
    public List<LoanCategory> findAll() {
        return loanCategoryRepository.findAll();
    }
}

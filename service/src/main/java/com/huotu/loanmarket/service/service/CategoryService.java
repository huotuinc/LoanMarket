package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.LoanCategory;

import java.util.List;

public interface CategoryService extends CrudService<LoanCategory,Integer>{


    List<LoanCategory> findAll();

}

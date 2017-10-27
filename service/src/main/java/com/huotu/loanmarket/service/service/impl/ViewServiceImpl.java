package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.LoanViewLog;
import com.huotu.loanmarket.service.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class ViewServiceImpl extends AbstractCrudService<LoanViewLog, Integer> implements ViewService {
    @Autowired
    public ViewServiceImpl(JpaCrudRepository<LoanViewLog, Integer> repository) {
        super(repository);
    }
}

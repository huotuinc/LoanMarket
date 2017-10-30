package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanUserViewLog;
import com.huotu.loanmarket.service.repository.LoanUserViewLogRepository;
import com.huotu.loanmarket.service.service.ViewLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class ViewLogServiceImpl extends AbstractCrudService<LoanUserViewLog, Integer> implements ViewLogService {
    
    @Autowired
    public ViewLogServiceImpl(LoanUserViewLogRepository repository) {
        super(repository);
    }

    @Override
    public LoanUserViewLog log(int userId, int projectId) {
        LoanUserViewLog userViewLog = new LoanUserViewLog();
        userViewLog.setProjectId(projectId);
        userViewLog.setUserId(userId);
        userViewLog.setViewTime(new Date());

        return this.save(userViewLog);
    }
}

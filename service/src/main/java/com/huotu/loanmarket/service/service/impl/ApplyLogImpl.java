package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanUserApplyLog;
import com.huotu.loanmarket.service.repository.LoanUserApplyLogRepository;
import com.huotu.loanmarket.service.service.ApplyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class ApplyLogImpl extends AbstractCrudService<LoanUserApplyLog, Integer> implements ApplyLogService {
    @Autowired
    public ApplyLogImpl(LoanUserApplyLogRepository repository) {
        super(repository);
    }

    @Override
    public LoanUserApplyLog log(Long userId, int projectId) {
        LoanUserApplyLog userApplyLog = new LoanUserApplyLog();
        userApplyLog.setProjectId(projectId);
        userApplyLog.setUserId(userId);
        userApplyLog.setApplyTime(new Date());

        return this.save(userApplyLog);
    }
}

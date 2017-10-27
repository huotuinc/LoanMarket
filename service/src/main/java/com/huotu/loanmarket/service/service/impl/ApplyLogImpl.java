package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanApplyLog;
import com.huotu.loanmarket.service.repository.LoanApplyLogRepository;
import com.huotu.loanmarket.service.service.ApplyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class ApplyLogImpl extends AbstractCrudService<LoanApplyLog, Integer> implements ApplyLogService {
    @Autowired
    public ApplyLogImpl(LoanApplyLogRepository repository) {
        super(repository);
    }

    @Override
    public boolean applyCount(int userId, int projectId) {
        LoanApplyLog applyLog = this.repository.findOne(userId);
        if (applyLog != null) {
            applyLog.setNum(applyLog.getNum() + 1);
        } else {
            applyLog = new LoanApplyLog();
            applyLog.setNum(1);
            applyLog.setUserId(userId);
            applyLog.setProjectId(projectId);
        }
        LoanApplyLog loanApplyLog = this.repository.save(applyLog);
        return loanApplyLog != null;
    }
}

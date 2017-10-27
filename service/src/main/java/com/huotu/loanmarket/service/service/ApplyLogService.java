package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.LoanApplyLog;

/**
 *
 * @author hxh
 * @date 2017-10-27
 */
public interface ApplyLogService extends CrudService<LoanApplyLog,Integer>{
    boolean applyCount(int userId, int projectId);
}

package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.LoanUserApplyLog;

/**
 * @author hxh
 * @date 2017-10-27
 */
public interface ApplyLogService extends CrudService<LoanUserApplyLog, Integer> {
    /**
     * 记录申请量
     *
     * @param userId    用户id
     * @param projectId 产品id
     * @return
     */
    LoanUserApplyLog log(Long userId, int projectId);
}

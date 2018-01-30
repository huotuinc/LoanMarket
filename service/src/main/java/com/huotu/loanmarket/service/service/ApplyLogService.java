package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.project.ProjectApplyLog;

/**
 * @author hxh
 * @date 2017-10-27
 */
public interface ApplyLogService extends CrudService<ProjectApplyLog, Integer> {
    /**
     * 记录申请量
     *
     * @param userId    用户id
     * @param projectId 产品id
     * @return
     */
    ProjectApplyLog log(int userId, int projectId);
}

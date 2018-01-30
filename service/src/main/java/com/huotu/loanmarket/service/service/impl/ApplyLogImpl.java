package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.project.ProjectApplyLog;
import com.huotu.loanmarket.service.repository.Project.ProjectApplyLogRepository;
import com.huotu.loanmarket.service.service.ApplyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class ApplyLogImpl extends AbstractCrudService<ProjectApplyLog, Integer> implements ApplyLogService {
    @Autowired
    public ApplyLogImpl(ProjectApplyLogRepository repository) {
        super(repository);
    }

    @Override
    public ProjectApplyLog log(int userId, int projectId) {
        ProjectApplyLog userApplyLog = new ProjectApplyLog();
        userApplyLog.setProjectId(projectId);
        userApplyLog.setUserId(userId);
        userApplyLog.setApplyTime(new Date());

        return this.save(userApplyLog);
    }
}

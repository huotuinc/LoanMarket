package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.project.ProjectViewLog;
import com.huotu.loanmarket.service.repository.Project.ProjectViewLogRepository;
import com.huotu.loanmarket.service.service.ViewLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class ViewLogServiceImpl extends AbstractCrudService<ProjectViewLog, Integer> implements ViewLogService {
    
    @Autowired
    public ViewLogServiceImpl(ProjectViewLogRepository repository) {
        super(repository);
    }

    @Override
    public ProjectViewLog log(int userId, int projectId) {
        ProjectViewLog userViewLog = new ProjectViewLog();
        userViewLog.setProjectId(projectId);
        userViewLog.setUserId(userId);
        userViewLog.setViewTime(new Date());

        return this.save(userViewLog);
    }
}

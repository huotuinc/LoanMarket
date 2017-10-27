package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.searchable.ProjectSearchTopCondition;

import java.util.List;

/**
 * @author allan
 * @date 23/10/2017
 */
public interface ProjectService extends CrudService<LoanProject, Integer> {
    List<LoanProject> getProjectList(ProjectSearchCondition projectSearchCondition);

    List<LoanProject> getProjectTopList(ProjectSearchTopCondition projectSearchTopCondition);

    LoanProject getProjectDetail(int projectId, int userId);
}

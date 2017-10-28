package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author allan
 * @date 23/10/2017
 */
public interface ProjectService extends CrudService<LoanProject, Integer> {
    List<LoanProject> getProjectList(ProjectSearchCondition projectSearchCondition);

    LoanProject getProjectDetail(int projectId, int userId);

    Page<LoanProject> findAll(ProjectSearchCondition searchCondition);
}

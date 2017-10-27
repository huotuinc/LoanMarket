package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.repository.LoanProjectRepository;
import com.huotu.loanmarket.service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author allan
 * @date 23/10/2017
 */
@Service
public class ProjectServiceImpl extends AbstractCrudService<LoanProject, Integer> implements ProjectService {

    private final LoanProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(LoanProjectRepository repository) {
        super(repository);
        projectRepository = repository;
    }
}
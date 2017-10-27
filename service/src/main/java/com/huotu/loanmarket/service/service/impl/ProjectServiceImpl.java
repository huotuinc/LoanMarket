package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.CategoryRelation;
import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.entity.LoanViewLog;
import com.huotu.loanmarket.service.repository.LoanProjectRepository;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.service.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<LoanProject> getProjectList(ProjectSearchCondition projectSearchCondition) {
        List<Predicate> predicates = new ArrayList<>();
        Specification<LoanProject> specification = (root, criteriaQuery, criteriaBuilder) -> {
            //子查询
            Subquery subquery = criteriaQuery.subquery(CategoryRelation.class);
            Root changeLogRoot = subquery.from(CategoryRelation.class);
            Subquery categoryQuery = criteriaQuery.subquery(LoanCategory.class);
            Root categoryQueryRoot = categoryQuery.from(LoanCategory.class);
            //where
            predicates.add(criteriaBuilder.equal(changeLogRoot.get("loanProject").get("id").as(Integer.class), root.get("id").as(Integer.class)));
            predicates.add(criteriaBuilder.equal(changeLogRoot.get("loanCategory").get("id").as(Integer.class), categoryQueryRoot.get("id").as(Integer.class)));
            predicates.add(criteriaBuilder.equal(categoryQueryRoot.get("id").as(Integer.class), projectSearchCondition.getSid()));
            predicates.add(criteriaBuilder.equal(root.get("desc").as(String.class), projectSearchCondition.getDesc()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Pageable pageable = null;
        boolean flag = true;
        if (projectSearchCondition.getTopNum() != 0) {
            pageable = new PageRequest(0, projectSearchCondition.getTopNum());
            flag = false;
        }
        List<LoanProject> loanProjectList;
        if (flag) {
            Page<LoanProject> loanProjectPage = loanProjectRepository.findAll(specification, pageable);
            loanProjectList = loanProjectPage.getContent();
        } else {
            loanProjectList = loanProjectRepository.findAll(specification);
        }
        return loanProjectList;
    }

    @Override
    public LoanProject getProjectDetail(int projectId, int userId) {
        LoanProject project = this.repository.findOne(projectId);
        if (userId > 0) {
            //记录浏览量量
            LoanViewLog viewLog = viewService.findOne(userId);
            if (viewLog != null) {
                viewLog.setNum(viewLog.getNum() + 1);
            } else {
                viewLog = new LoanViewLog();
                viewLog.setNum(1);
                viewLog.setProjectId(projectId);
                viewLog.setUserId(userId);
            }
            viewService.save(viewLog);
        }
        return project;
    }
}
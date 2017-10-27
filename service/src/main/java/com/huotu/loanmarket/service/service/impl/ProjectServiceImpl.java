package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.CategoryRelation;
import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.entity.LoanViewLog;
import com.huotu.loanmarket.service.repository.LoanProjectRepository;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.searchable.ProjectSearchTopCondition;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.service.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private ViewService viewService;
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
            predicates.add(criteriaBuilder.equal(root.get("isHot").as(Integer.class), projectSearchCondition.getIsHot()));
            predicates.add(criteriaBuilder.equal(root.get("isNew").as(Integer.class), projectSearchCondition.getIsNew()));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxMoney").as(Float.class),projectSearchCondition.getMoney()));
            if (!StringUtils.isEmpty(projectSearchCondition.getName())) {
                predicates.add(criteriaBuilder.equal(root.get("name").as(String.class), projectSearchCondition.getName()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Sort sort;
        if (projectSearchCondition.getSortType() == 0) {
            sort = new Sort(Sort.Direction.ASC, "createTime");
        } else {
            sort = new Sort(Sort.Direction.DESC, "createTime");
        }
        if(projectSearchCondition.getPageIndex()==0){
            projectSearchCondition.setPageIndex(1);
        }
        Pageable pageable = new PageRequest(projectSearchCondition.getPageIndex()-1, projectSearchCondition.getPageSize(),sort);

        return projectRepository.findAll(specification, pageable).getContent();
    }

    @Override
    public List<LoanProject> getProjectTopList(ProjectSearchTopCondition projectSearchTopCondition) {
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
            predicates.add(criteriaBuilder.equal(categoryQueryRoot.get("id").as(Integer.class), projectSearchTopCondition.getSid()));
            predicates.add(criteriaBuilder.equal(root.get("isHot").as(Integer.class), projectSearchTopCondition.getIsHot()));
            predicates.add(criteriaBuilder.equal(root.get("isNew").as(Integer.class), projectSearchTopCondition.getIsNew()));
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxMoney").as(Float.class),projectSearchTopCondition.getMoney()));
            if (!StringUtils.isEmpty(projectSearchTopCondition.getName())) {
                predicates.add(criteriaBuilder.equal(root.get("name").as(String.class), projectSearchTopCondition.getName()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Sort sort;
        if (projectSearchTopCondition.getSortType() == 0) {
            sort = new Sort(Sort.Direction.ASC, "createTime");
        } else {
            sort = new Sort(Sort.Direction.DESC, "createTime");
        }
        Pageable pageable = null;
        boolean flag = true;
        if (projectSearchTopCondition.getTopNum() > 0) {
            pageable = new PageRequest(0, projectSearchTopCondition.getTopNum(),sort);
            flag = false;
        }
        List<LoanProject> loanProjectList;
        if (flag) {
            Page<LoanProject> loanProjectPage = projectRepository.findAll(specification, pageable);
            loanProjectList = loanProjectPage.getContent();
        } else {
            loanProjectList = projectRepository.findAll(specification);
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
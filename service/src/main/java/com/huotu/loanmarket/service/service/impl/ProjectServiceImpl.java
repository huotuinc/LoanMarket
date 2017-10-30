package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.repository.LoanProjectRepository;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
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

//    @Override
//    public List<LoanProject> getProjectTopList(ProjectSearchTopCondition projectSearchTopCondition) {
//        List<Predicate> predicates = new ArrayList<>();
//        Specification<LoanProject> specification = (root, criteriaQuery, criteriaBuilder) -> {
//            //子查询
//            Subquery subquery = criteriaQuery.subquery(CategoryRelation.class);
//            Root changeLogRoot = subquery.from(CategoryRelation.class);
//            Subquery categoryQuery = criteriaQuery.subquery(LoanCategory.class);
//            Root categoryQueryRoot = categoryQuery.from(LoanCategory.class);
//            //where
//            predicates.add(criteriaBuilder.equal(changeLogRoot.get("loanProject").get("id").as(Integer.class), root.get("id").as(Integer.class)));
//            predicates.add(criteriaBuilder.equal(changeLogRoot.get("loanCategory").get("id").as(Integer.class), categoryQueryRoot.get("id").as(Integer.class)));
//            predicates.add(criteriaBuilder.equal(categoryQueryRoot.get("id").as(Integer.class), projectSearchTopCondition.getSid()));
//            predicates.add(criteriaBuilder.equal(root.get("isHot").as(Integer.class), projectSearchTopCondition.getIsHot()));
//            predicates.add(criteriaBuilder.equal(root.get("isNew").as(Integer.class), projectSearchTopCondition.getIsNew()));
//            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxMoney").as(Float.class), projectSearchTopCondition.getMoney()));
//            if (!StringUtils.isEmpty(projectSearchTopCondition.getName())) {
//                predicates.add(criteriaBuilder.equal(root.get("name").as(String.class), projectSearchTopCondition.getName()));
//            }
//            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//        };
//        Sort sort;
//        if (projectSearchTopCondition.getSortType() == 0) {
//            sort = new Sort(Sort.Direction.ASC, "createTime");
//        } else {
//            sort = new Sort(Sort.Direction.DESC, "createTime");
//        }
//        Pageable pageable = null;
//        boolean flag = true;
//        if (projectSearchTopCondition.getTopNum() > 0) {
//            pageable = new PageRequest(0, projectSearchTopCondition.getTopNum(), sort);
//            flag = false;
//        }
//        List<LoanProject> loanProjectList;
//        if (flag) {
//            Page<LoanProject> loanProjectPage = projectRepository.findAll(specification, pageable);
//            loanProjectList = loanProjectPage.getContent();
//        } else {
//            loanProjectList = projectRepository.findAll(specification);
//        }
//        return loanProjectList;
//    }

    @Override
    public Page<LoanProject> findAll(int pageIndex, int pageSize, ProjectSearchCondition searchCondition) {
        Specification<LoanProject> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchCondition.getIsHot() != -1) {
                predicates.add(cb.equal(root.get("isHot").as(Integer.class), searchCondition.getIsHot()));
            }
            if (searchCondition.getIsNew() != -1) {
                predicates.add(cb.equal(root.get("isNew").as(Integer.class), searchCondition.getIsNew()));
            }
            if (searchCondition.getCategoryId() > 0) {
                String categoryStr = "," + searchCondition.getCategoryId() + ",";
                predicates.add(cb.like(root.get("categories").as(String.class), "%" + categoryStr + "%"));
            }
            if (searchCondition.getLoanMoney() > 0) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("maxMoney").as(Double.class), searchCondition.getLoanMoney()));
            }
            if (!StringUtils.isEmpty(searchCondition.getName())) {
                predicates.add(cb.like(root.get("Name").as(String.class), searchCondition.getName()));
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable;
        if (searchCondition.getTopNum() > 0) {
            pageable = new PageRequest(0, searchCondition.getTopNum(), sort);
        } else {
            pageable = new PageRequest(pageIndex - 1, pageSize, sort);
        }

        return projectRepository.findAll(specification, pageable);
    }


    @Override
    public List<LoanProject> getHotProject() {
        List<Predicate> predicates = new ArrayList<>();
        Specification<LoanProject> specification = (root, criteriaQuery, criteriaBuilder) -> {
            //子查询
//            Subquery subquery = criteriaQuery.subquery(CategoryRelation.class);
//            Root changeLogRoot = subquery.from(CategoryRelation.class);
//            Subquery categoryQuery = criteriaQuery.subquery(LoanCategory.class);
//            Root categoryQueryRoot = categoryQuery.from(LoanCategory.class);
            //where
            //predicates.add(criteriaBuilder.equal(changeLogRoot.get("loanProject").get("id").as(Integer.class), root.get("id").as(Integer.class)));
            //predicates.add(criteriaBuilder.equal(changeLogRoot.get("loanCategory").get("id").as(Integer.class), categoryQueryRoot.get("id").as(Integer.class)));
            //predicates.add(criteriaBuilder.equal(categoryQueryRoot.get("id").as(Integer.class), projectSearchTopCondition.getSid()));
            predicates.add(criteriaBuilder.equal(root.get("isHot").as(Integer.class), 1));
            //predicates.add(criteriaBuilder.equal(root.get("isNew").as(Integer.class), projectSearchTopCondition.getIsNew()));
            //predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxMoney").as(Float.class), projectSearchTopCondition.getMoney()));
            //if (!StringUtils.isEmpty(projectSearchTopCondition.getName())) {
            //    predicates.add(criteriaBuilder.equal(root.get("name").as(String.class), projectSearchTopCondition.getName()));
            //}
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        //Sort sort;
        //if (projectSearchTopCondition.getSortType() == 0) {
        //    sort = new Sort(Sort.Direction.ASC, "createTime");
        //} else {
        //    sort = new Sort(Sort.Direction.DESC, "createTime");
        //}
        List<LoanProject> loanProjectList = projectRepository.findAll(specification);

        return loanProjectList;
    }

    @Override
    public List<LoanProject> getNewProject() {
        List<Predicate> predicates = new ArrayList<>();
        Specification<LoanProject> specification = (root, criteriaQuery, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("isNew").as(Integer.class), 1));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        //Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<LoanProject> loanProjectList = projectRepository.findAll(specification);
        return loanProjectList;
    }
}
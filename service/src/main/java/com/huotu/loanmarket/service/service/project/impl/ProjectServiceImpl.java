package com.huotu.loanmarket.service.service.project.impl;

import com.huotu.loanmarket.service.entity.project.Project;
import com.huotu.loanmarket.service.entity.project.ProjectApplyLog;
import com.huotu.loanmarket.service.entity.project.ProjectViewLog;
import com.huotu.loanmarket.service.repository.project.ProjectApplyLogRepository;
import com.huotu.loanmarket.service.repository.project.ProjectRepository;
import com.huotu.loanmarket.service.model.projectsearch.ProjectSearchCondition;
import com.huotu.loanmarket.service.repository.project.ProjectViewLogRepository;
import com.huotu.loanmarket.service.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author allan
 * @date 23/10/2017
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectViewLogRepository projectViewLogRepository;
    @Autowired
    private ProjectApplyLogRepository projectApplyLogRepository;


    @Override
    public Page<Project> findAll(int pageIndex, int pageSize, ProjectSearchCondition searchCondition) {
        Specification<Project> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isDelete").as(Integer.class), 0));
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
                predicates.add(cb.like(root.get("name").as(String.class), "%" + searchCondition.getName() + "%"));
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
    public List<Project> findAll(int categoryId, double amount, int deadline) {
        Specification<Project> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isDelete").as(Integer.class), 0));
            String categoryStr = "," + categoryId + ",";
            predicates.add(cb.like(root.get("categories").as(String.class), "%" + categoryStr + "%"));
            predicates.add(cb.greaterThanOrEqualTo(root.get("maxMoney").as(Double.class), amount));
            predicates.add(cb.equal(root.get("deadlineUnit").as(Integer.class), 1));
            predicates.add(cb.greaterThanOrEqualTo(root.get("maxDeadline").as(Integer.class), deadline));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return projectRepository.findAll(specification);
    }

    @Override
    public List<Project> findAll() {
        List<Predicate> predicates = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Specification<Project> specification = (root, criteriaQuery, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("isDelete").as(Integer.class), 0));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return projectRepository.findAll(specification, sort);
    }


    @Override
    public List<Project> getHotProject() {
        List<Predicate> predicates = new ArrayList<>();
        Specification<Project> specification = (root, criteriaQuery, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("isDelete").as(Integer.class), 0));
            predicates.add(criteriaBuilder.equal(root.get("isHot").as(Integer.class), 1));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Sort sort = new Sort(Sort.Direction.DESC, "topSortNum");
        Pageable pageable = new PageRequest(0, 20, sort);

        Page<Project> list = projectRepository.findAll(specification, pageable);
        return list.getContent();
    }

    @Override
    public List<Project> getNewProject() {
        List<Predicate> predicates = new ArrayList<>();
        Specification<Project> specification = (root, criteriaQuery, criteriaBuilder) -> {
            predicates.add(criteriaBuilder.equal(root.get("isDelete").as(Integer.class), 0));
            predicates.add(criteriaBuilder.equal(root.get("isNew").as(Integer.class), 1));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = new PageRequest(0, 12, sort);
        Page<Project> list = projectRepository.findAll(specification, pageable);
        return list.getContent();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setHot(int isHot, String projectIdsStr) {
        List<Integer> projectIds = projectIds(projectIdsStr);
        projectRepository.setHot(isHot, projectIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setNew(int isNew, String projectIdsStr) {
        List<Integer> projectIds = projectIds(projectIdsStr);
        projectRepository.setNew(isNew, projectIds);
    }

    @Override
    public List<Project> findByTag(int tag) {
        Specification<Project> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isDelete").as(Integer.class), 0));
            String categoryStr = "," + tag + ",";
            predicates.add(cb.like(root.get("categories").as(String.class), "%" + categoryStr + "%"));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return projectRepository.findAll(specification, sort);
    }

    @Override
    public Project findOne(Integer projectId) {
        return projectRepository.findOne(projectId);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    private List<Integer> projectIds(String projectIdsStr) {
        String[] projectIdsArray = projectIdsStr.split(",");
        List<Integer> projectIds = new ArrayList<>();
        for (String s : projectIdsArray) {
            projectIds.add(Integer.valueOf(s));
        }
        return projectIds;
    }

    @Override
    public ProjectViewLog logView(int userId, int projectId) {
        ProjectViewLog userViewLog = new ProjectViewLog();
        userViewLog.setProjectId(projectId);
        userViewLog.setUserId(userId);
        userViewLog.setViewTime(new Date());

        return projectViewLogRepository.save(userViewLog);
    }

    @Override
    public ProjectApplyLog logApply(int userId, int projectId) {
        ProjectApplyLog userApplyLog = new ProjectApplyLog();
        userApplyLog.setProjectId(projectId);
        userApplyLog.setUserId(userId);
        userApplyLog.setApplyTime(new Date());

        return projectApplyLogRepository.save(userApplyLog);
    }
}
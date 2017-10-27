package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.entity.CategoryRelation;
import com.huotu.loanmarket.service.entity.LoanApplyLog;
import com.huotu.loanmarket.service.entity.LoanCategory;
import com.huotu.loanmarket.service.entity.LoanEquipment;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.entity.LoanViewLog;
import com.huotu.loanmarket.service.repository.LoanApplyLogRepository;
import com.huotu.loanmarket.service.repository.LoanCategoryRepository;
import com.huotu.loanmarket.service.repository.LoanEquipmentRepository;
import com.huotu.loanmarket.service.repository.LoanProjectRepository;
import com.huotu.loanmarket.service.repository.LoanUserRepository;
import com.huotu.loanmarket.service.repository.LoanViewLogRepository;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.service.service.ApiService;
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
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private LoanEquipmentRepository loanEquipmentRepository;
    @Autowired
    private LoanUserRepository loanUserRepository;
    @Autowired
    private LoanCategoryRepository loanCategoryRepository;
    @Autowired
    private LoanProjectRepository loanProjectRepository;
    @Autowired
    private LoanViewLogRepository loanViewLogRepository;
    @Autowired
    private LoanApplyLogRepository loanApplyLogRepository;

    @Override
    public LoanEquipment saveAppInfo(String appVersion, String osVersion, String osType) {
        LoanEquipment loanEquipment = new LoanEquipment();
        loanEquipment.setAppVersion(appVersion);
        loanEquipment.setOsType(osType);
        loanEquipment.setOsVersion(osVersion);
        return loanEquipmentRepository.saveAndFlush(loanEquipment);
    }

    @Override
    public LoanUser init(int userId) {
        if (userId > 0) {
            return loanUserRepository.findOne(userId);
        }
        return null;
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
            predicates.add(criteriaBuilder.equal(changeLogRoot.get("loanProject").get("id").as(Integer.class),root.get("id").as(Integer.class)));
            predicates.add(criteriaBuilder.equal(changeLogRoot.get("loanCategory").get("id").as(Integer.class),categoryQueryRoot.get("id").as(Integer.class)));
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
    public List<LoanCategory> getProjectCategory() {
        return loanCategoryRepository.findAll();
    }

    @Override
    public LoanProject getProjectDetail(int projectId, int userId) {
        LoanProject loanProject = loanProjectRepository.findOne(projectId);
        if (userId > 0) {
            //记录浏览量量
            LoanViewLog viewLog = loanViewLogRepository.findOne(userId);
            if (viewLog != null) {
                viewLog.setNum(viewLog.getNum() + 1);
            } else {
                viewLog = new LoanViewLog();
                viewLog.setNum(1);
                viewLog.setProjectId(projectId);
                viewLog.setUserId(userId);
            }
            loanViewLogRepository.save(viewLog);
        }
        return loanProject;
    }

    @Override
    public boolean checkLogin(String mobile, String verifyCode) {
        // TODO: 2017-10-26 验证码待测
        LoanUser user = loanUserRepository.findByAccount(mobile);
        if (user == null) {
            user = new LoanUser();
            user.setAccount(mobile);
            loanUserRepository.save(user);
        }
        return true;
    }

    @Override
    public LoanApplyLog applyCount(int userId, int projectId) {
        LoanApplyLog applyLog = loanApplyLogRepository.findOne(userId);
        if (applyLog != null) {
            applyLog.setNum(applyLog.getNum() + 1);
        } else {
            applyLog = new LoanApplyLog();
            applyLog.setNum(1);
            applyLog.setUserId(userId);
            applyLog.setProjectId(projectId);
        }
       return loanApplyLogRepository.save(applyLog);
    }
}

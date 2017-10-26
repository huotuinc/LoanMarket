package com.huotu.loanmarket.web.controller.api.impl;

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
import com.huotu.loanmarket.web.common.ApiResult;
import com.huotu.loanmarket.web.common.ResultCodeEnum;
import com.huotu.loanmarket.web.controller.api.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allan
 * @date 23/10/2017
 */
@Controller
@RequestMapping("/rest/api")
public class ApiControllerImpl implements ApiController {
    @Autowired
    private LoanUserRepository loanUserRepository;
    @Autowired
    private LoanProjectRepository loanProjectRepository;
    @Autowired
    private LoanEquipmentRepository loanEquipmentRepository;
    @Autowired
    private LoanCategoryRepository loanCategoryRepository;
    @Autowired
    private LoanApplyLogRepository loanApplyLogRepository;
    @Autowired
    private LoanViewLogRepository loanViewLogRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public ApiResult appInfo(String appVersion, String osVersion, String osType) {
        LoanEquipment loanEquipment = new LoanEquipment();
        loanEquipment.setAppVersion(appVersion);
        loanEquipment.setOsType(osType);
        loanEquipment.setOsVersion(osVersion);
        loanEquipmentRepository.saveAndFlush(loanEquipment);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }

    @Override
    public ApiResult init(Long userId) {
        // TODO: 2017-10-26
        //App版本信息,存在即表示有更新，否则为null
        //如果用户未登录返回null
        if (userId > 0) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS, loanUserRepository.findOne(userId));
        }
        return null;
    }

    @Override
    public ApiResult projectList(ProjectSearchCondition projectSearchCondition) {
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
        List<LoanProject> loanProjectList = new ArrayList<>();
        if (flag) {
            Page<LoanProject> loanProjectPage = loanProjectRepository.findAll(specification, pageable);
            loanProjectList = loanProjectPage.getContent();
        } else {
            loanProjectList = loanProjectRepository.findAll(specification);
        }
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, loanProjectList);
    }

    @Override
    public ApiResult projectCategory() {
        List<LoanCategory> categoryList = loanCategoryRepository.findAll();
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, categoryList);
    }

    @Override
    public ApiResult projectDetail(int projectId, int userId) {
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
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, loanProject);
    }

    @Override
    public ApiResult login(String mobile, String verifyCode) {
        // TODO: 2017-10-26
        LoanUser user = loanUserRepository.findOne(Long.parseLong(mobile));
        if (user != null) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }

    @Override
    public ApiResult applyCount(int userId, int projectId) {
        LoanApplyLog applyLog = loanApplyLogRepository.findOne(userId);
        if (applyLog != null) {
            applyLog.setNum(applyLog.getNum() + 1);
        } else {
            applyLog = new LoanApplyLog();
            applyLog.setNum(1);
            applyLog.setUserId(userId);
            applyLog.setProjectId(projectId);
        }
        loanApplyLogRepository.save(applyLog);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
    }
}

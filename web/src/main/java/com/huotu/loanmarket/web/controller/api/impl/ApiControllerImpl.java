package com.huotu.loanmarket.web.controller.api.impl;

import com.huotu.loanmarket.service.repositoryhxh.LoanProjectRepository;
import com.huotu.loanmarket.service.repositoryhxh.LoanUserRepository;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.searchable.ProjectSearchCondition;
import com.huotu.loanmarket.web.common.ApiResult;
import com.huotu.loanmarket.web.common.ResultCodeEnum;
import com.huotu.loanmarket.web.controller.api.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.Predicate;
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

    @Override
    public ApiResult appInfo(String appVersion, String osVersion, String osType) {
        return null;
    }

    @Override
    public ApiResult init(Long userId) {
        //App版本信息,存在即表示有更新，否则为null
        //开机广告信息
        //如果用户未登录返回null
        if (userId > 0) {
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS, loanUserRepository.findOne(userId));
        }
        return null;
    }

    @Override
    public ApiResult projectList(ProjectSearchCondition projectSearchCondition) {
        Specification<LoanProject> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("desc").as(String.class), projectSearchCondition.getDesc()));
            predicates.add(criteriaBuilder.equal(root.get("topSortNum").as(Integer.class), projectSearchCondition.getTopNum()));
            predicates.add(criteriaBuilder.equal(root.join("categoryRelationList").join("loanCategory").get("id").as(Integer.class), projectSearchCondition.getSid()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        List<LoanProject> loanProjectList = loanProjectRepository.findAll(specification);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, loanProjectList);
    }

    @Override
    public ApiResult projectCategory() {

        return null;
    }

    @Override
    public ApiResult projectDetail(int projectId, int userId) {
        LoanProject loanProject = loanProjectRepository.findOne(projectId);
        if(projectId>0){
            //记录浏览量量
        }
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS,loanProject);
    }

    @Override
    public ApiResult login(String mobile, String verifyCode) {
        LoanUser user = loanUserRepository.findOne(Long.parseLong(mobile));
        if(user!=null){
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }

    @Override
    public ApiResult applyCount(int userId, int projectId) {
        return null;
    }
}

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.system.impl;

import com.huotu.loanmarket.service.entity.system.AppSystemVersion;
import com.huotu.loanmarket.service.enums.DeviceTypeEnum;
import com.huotu.loanmarket.service.model.PageListView;
import com.huotu.loanmarket.service.repository.system.AppVersionRepository;
import com.huotu.loanmarket.service.service.system.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guomw
 * @date 2017/12/26
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionRepository appVersionRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long itemId) {
        appVersionRepository.delete(itemId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppSystemVersion save(AppSystemVersion appVersion) {
        return appVersionRepository.saveAndFlush(appVersion);
    }

    @Override
    public AppSystemVersion findOne(Long objId) {
        return appVersionRepository.findOne(objId);
    }

    /**
     * 获取最近的一条版本更新记录
     *
     * @param deviceTypeEnum
     * @return
     */
    @Override
    public AppSystemVersion findLastOne(DeviceTypeEnum deviceTypeEnum) {
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.DESC, "vid");

        Specification<AppSystemVersion> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deviceType").as(DeviceTypeEnum.class), deviceTypeEnum));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<AppSystemVersion> page = appVersionRepository.findAll(specification,pageable);
        List<AppSystemVersion> versionList = page.getContent();
        if (versionList != null && versionList.size() > 0) {
            return versionList.get(0);
        }
        return null;
    }

    /**
     *
     * @param deviceTypeEnum
     * @param appVersion
     * @return
     */
    @Override
    public AppSystemVersion findByAppVersion(DeviceTypeEnum deviceTypeEnum,String appVersion) {
        return appVersionRepository.findByVersion(deviceTypeEnum,appVersion);
    }

    @Override
    public PageListView<AppSystemVersion> findAll(Integer updateType, Integer pageIndex, Integer pageSize) {
        PageListView<AppSystemVersion> result = new PageListView<>();
        Specification<AppSystemVersion> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (updateType != null && updateType >= 0) {
                predicates.add(criteriaBuilder.equal(root.get("updateType").as(Integer.class), updateType));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Sort sort = new Sort(Sort.Direction.DESC, "vid");
        Page<AppSystemVersion> page = appVersionRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize, sort));
        result.setTotalCount(page.getTotalElements());
        result.setPageCount(page.getTotalPages());
        result.setList(page.getContent());
        return result;
    }
}

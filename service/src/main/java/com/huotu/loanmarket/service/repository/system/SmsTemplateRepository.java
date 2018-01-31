/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.repository.system;

import com.huotu.loanmarket.service.entity.system.SmsTemple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author guomw
 * @date 2017/12/15
 */
@Repository
public interface SmsTemplateRepository extends JpaRepository<SmsTemple,Integer>,JpaSpecificationExecutor<SmsTemple> {

    /**
     * 获取短信模板
     * @param sceneType
     * @return
     */
    @Query("select temp from SmsTemple temp where  temp.sceneType=?1")
    SmsTemple findBySceneType(Integer sceneType);



}

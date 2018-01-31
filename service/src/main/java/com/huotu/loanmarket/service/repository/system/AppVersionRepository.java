/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.repository.system;

import com.huotu.loanmarket.service.entity.system.AppSystemVersion;
import com.huotu.loanmarket.service.enums.DeviceTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author guomw
 * @date 29/11/2017
 */
@Repository
public interface AppVersionRepository extends JpaRepository<AppSystemVersion, Long>,JpaSpecificationExecutor<AppSystemVersion> {

    /**
     * 根据版本号，获取数据
     * @param deviceTypeEnum
     * @param version
     * @return
     */
    @Query("select v from AppSystemVersion v where v.deviceType=?1 and v.version=?2")
    AppSystemVersion findByVersion(DeviceTypeEnum deviceTypeEnum,String version);
}

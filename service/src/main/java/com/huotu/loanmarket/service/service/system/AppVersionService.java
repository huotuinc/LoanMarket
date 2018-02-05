/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.system;

import com.huotu.loanmarket.service.entity.system.AppSystemVersion;
import com.huotu.loanmarket.service.enums.DeviceTypeEnum;
import com.huotu.loanmarket.service.enums.PackageTypeEnum;
import com.huotu.loanmarket.service.model.PageListView;

/**
 *
 * @author guomw
 * @date 2017/12/26
 */
public interface AppVersionService {


    /**
     * 删除数据
     * @param itemId
     * @return
     */
    boolean delete(Long itemId);

    /**
     * 更新变包类型
     * @param itemId
     * @param packageTypeEnum
     * @return
     */
    boolean updatePackageType(Long itemId, PackageTypeEnum packageTypeEnum);

    /**
     * 保存消息
     *
     * @param appVersion
     * @return
     */
    AppSystemVersion save(AppSystemVersion appVersion);
    /**
     * 获取消息
     *
     * @param objId
     * @return
     */
    AppSystemVersion findOne(Long objId);

    /**
     * 获取最后一条数据
     * @param deviceTypeEnum
     * @return
     */
    AppSystemVersion findLastOne(DeviceTypeEnum deviceTypeEnum);

    /**
     * 根据版本号，获取实体数据
     * @param deviceTypeEnum
     * @param appVersion
     * @return
     */
    AppSystemVersion findByAppVersion(DeviceTypeEnum deviceTypeEnum,String appVersion);

    /**
     * 获取数据列表
     *
     * @param updateType 模板类型
     * @param pageIndex 页码
     * @param pageSize 每页数量
     * @return
     */
    PageListView<AppSystemVersion> findAll(Integer updateType, Integer pageIndex, Integer pageSize);

}

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.dbversion;

/**
 * @author lgh
 * @date 2016/2/27
 */
@FunctionalInterface
public interface VersionUpgrade<T> {

    /**
     * 从最近版本升级到step版本.
     *
     * @param version 要升级的版本
     * @throws Exception
     */
    void upgradeToVersion(T version) throws Exception;
}

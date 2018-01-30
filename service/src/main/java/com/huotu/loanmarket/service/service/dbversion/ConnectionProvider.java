/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.dbversion;

import org.eclipse.persistence.platform.database.DatabasePlatform;

import java.sql.Connection;

/**
 * 可提供,可包容一个jdbc {@link Connection}的接口
 *
 * @author CJ
 */
public interface ConnectionProvider {

    /**
     * 获取jdbc链接
     *
     * @return
     */
    Connection getConnection();

    /**
     * @return
     */
    DatabasePlatform profile();

}

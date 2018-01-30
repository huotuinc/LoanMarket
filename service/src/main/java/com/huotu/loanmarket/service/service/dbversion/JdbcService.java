/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.dbversion;


import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author CJ
 */
public interface JdbcService {
    @FunctionalInterface
    interface ConnectionConsumer {

        /**
         * xx
         * @param connection
         * @throws SQLException
         * @throws IOException
         */
        void workWithConnection(ConnectionProvider connection) throws SQLException, IOException;
    }


    /**
     * 执行一些jdbc操作.
     * <p>
     * tip:操作数据表比如create,drop,alert将直接提交事务.
     * </p>
     *
     * @param connectionConsumer 操作者
     * @throws SQLException
     * @throws IOException
     */
    @Transactional(rollbackFor = Exception.class)
    void runJdbcWork(ConnectionConsumer connectionConsumer) throws SQLException, IOException;

    /**
     * 执行一些jdbc操作.
     * <p>
     * tip:操作数据表比如create,drop,alert将直接提交事务.
     * </p>
     * 跟{@link #runJdbcWork(ConnectionConsumer)}不同的是,该方法并没有声明使用事务,也就是它将依赖当前线程已开启的事务
     *
     * @param connectionConsumer 操作者
     * @throws SQLException
     * @throws IOException
     */
    void runStandaloneJdbcWork(ConnectionConsumer connectionConsumer) throws SQLException, IOException;

}

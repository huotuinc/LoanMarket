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
import org.eclipse.persistence.platform.database.MySQLPlatform;
import org.eclipse.persistence.platform.database.SQLServerPlatform;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author CJ
 */
public class SimpleConnectionProvider implements ConnectionProvider {
    private final Connection connection;
    private final DatabasePlatform profile;

    public SimpleConnectionProvider(Connection connection) throws SQLException {
        this.connection = connection;
        String databaseName = JdbcUtils.commonDatabaseName(connection.getMetaData().getDatabaseProductName());
        if (databaseName.equalsIgnoreCase("MySQL")) {
            profile = new MySQLPlatform();
        } else if (databaseName.equalsIgnoreCase("Microsoft SQL Server")) {
            profile = new SQLServerPlatform();
        } else {
            throw new InternalError("unsupported Database " + databaseName);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public DatabasePlatform profile() {
        return profile;
    }
}

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.dbversion;


import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.entity.SystemConfig;
import com.huotu.loanmarket.service.enums.CommonVersion;
import com.huotu.loanmarket.service.repository.SystemConfigRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Statement;
import java.util.List;


/**
 * @author lgh
 * @date 2016/4/19
 */
@Service
public class AppService implements ApplicationListener<ContextRefreshedEvent> {
    private static Log log = LogFactory.getLog(AppService.class);

    @Autowired
    private SystemConfigRepository systemConfigRepository;
    @Autowired
    private JdbcService jdbcService;
    @Autowired
    private BaseService baseService;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (event.getApplicationContext().getParent() == null) {
            SystemConfig systemConfig = systemConfigRepository.findOne("DatabaseVersion");
            if (systemConfig == null) {
                SystemConfig databaseVersion = new SystemConfig();
                databaseVersion.setCode("DatabaseVersion");
                databaseVersion.setValueForCode(String.valueOf(CommonVersion.initVersion.ordinal()));
                systemConfigRepository.save(databaseVersion);
            }

            if (!env.acceptsProfiles(Constant.PROFILE_UNIT_TEST)) {

                CommonVersion currentVersion=CommonVersion.initVersion;
                //系统升级
                baseService.systemUpgrade("DatabaseVersion", CommonVersion.class, currentVersion, (upgrade) -> {
                    switch (upgrade) {
                        case initVersion:
                            break;
                        default:
                            break;
                    }


                });
            }

        }
    }

    /**
     * 批量运行jdbc操作
     * @param listSql
     * @param commonVersion
     */
    private void runJdbcWork(List<String> listSql,CommonVersion commonVersion) {
        try {
            if (listSql == null || listSql.size() == 0) {
                return;
            }
            for (String hql : listSql) {
                runJdbcWork(hql,commonVersion);
            }
        } catch (Exception e) {
            log.error("update to" + commonVersion.ordinal() + " error", e);
        }
    }


    /**
     * 运行jdbc操作
     * @param hql
     * @param commonVersion
     */
    private void runJdbcWork(String hql,CommonVersion commonVersion) {
        try {
            jdbcService.runJdbcWork(connection -> {
                Statement statement = connection.getConnection().createStatement();
                statement.execute(hql);
            });
        } catch (Exception e) {
            log.error("update to" + commonVersion.ordinal() + ",sql[" + hql + "] error", e);
        }
    }
}

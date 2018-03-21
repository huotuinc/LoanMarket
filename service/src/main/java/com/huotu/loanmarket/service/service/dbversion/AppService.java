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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Statement;
import java.util.ArrayList;
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
                CommonVersion currentVersion = CommonVersion.version101;
                //系统升级
                this.systemUpgrade("DatabaseVersion", CommonVersion.class, currentVersion, (upgrade) -> {
                    switch (upgrade) {
                        case initVersion:
                            break;
                        case version101:
                            List<String> listSql=new ArrayList<>();
                            listSql.add("ALTER TABLE zx_users ADD credit_value INT DEFAULT 0 NULL;");
                            runJdbcWork(listSql,upgrade);
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
     *
     * @param listSql
     * @param commonVersion
     */
    private void runJdbcWork(List<String> listSql, CommonVersion commonVersion) {
        try {
            if (listSql == null || listSql.size() == 0) {
                return;
            }
            for (String hql : listSql) {
                runJdbcWork(hql, commonVersion);
            }
        } catch (Exception e) {
            log.error("update to" + commonVersion.ordinal() + " error", e);
        }
    }


    /**
     * 运行jdbc操作
     *
     * @param hql
     * @param commonVersion
     */
    private void runJdbcWork(String hql, CommonVersion commonVersion) {
        try {
            jdbcService.runJdbcWork(connection -> {
                Statement statement = connection.getConnection().createStatement();
                statement.execute(hql);
            });
        } catch (Exception e) {
            log.error("update to" + commonVersion.ordinal() + ",sql[" + hql + "] error", e);
        }
    }


    /**
     * 尝试系统升级,在发现需要升级以后将调用升级者,可以通过JDBC操作数据表.
     * <p>
     * <p>
     * 需要注意的是,版本升级采用的是逐步升级策略,比如数据库标记版本为1.0 然后更新到3.0 中间还存在2.0(这也是为什么版本标记是用枚举保
     * 存的原因),那么会让升级者升级到2.0再到3.0
     * </p>
     * <p>
     * 如果没有发现数据库版本标记 那么就默认为已经是当前版本了.
     * </p>
     *
     * @param systemStringVersionKey 保存版本信息的key,必须确保唯一;如果当前没有相关信息,则认为已经是当前版本了.
     * @param clazz                  维护版本信息的枚举类
     * @param currentVersion         当前版本
     * @param upgrade                负责提供系统升级业务的升级者
     * @param <T>                    维护版本信息的枚举类
     */
    @Transactional(rollbackFor = RuntimeException.class)
    private <T extends Enum> void systemUpgrade(String systemStringVersionKey, Class<T> clazz
            , T currentVersion, VersionUpgrade<T> upgrade) {

        log.info("system update to " + currentVersion);
        SystemConfig databaseVersion = systemConfigRepository.findOne(systemStringVersionKey);
        try {
            if (databaseVersion == null) {
                databaseVersion = new SystemConfig();
                databaseVersion.setCode(systemStringVersionKey);
                databaseVersion.setValueForCode(String.valueOf(currentVersion.ordinal()));
                systemConfigRepository.save(databaseVersion);
            } else {
                int version = Integer.parseInt(databaseVersion.getValueForCode());
                if (clazz.getEnumConstants().length > version) {
                    T database = clazz.getEnumConstants()[Integer.parseInt(databaseVersion.getValueForCode())];
                    if (database != currentVersion) {
                        upgrade(systemStringVersionKey, clazz, database, currentVersion, upgrade);
                    }
                }
            }
        } catch (Exception ex) {
            throw new InternalError("Failed Upgrade Database", ex);
        }

    }

    private <T extends Enum> void upgrade(String systemStringVersionKey, Class<T> clazz, T origin, T target, VersionUpgrade<T> upgrader)
            throws Exception {
        log.debug("Subsystem prepare to upgrade to " + target);
        boolean started = false;
        for (T step : clazz.getEnumConstants()) {
            if (origin == null || origin.ordinal() < step.ordinal()) {
                started = true;
            }

            if (started) {
                log.debug("Subsystem upgrade step: to " + target);
                upgrader.upgradeToVersion(step);
                log.debug("Subsystem upgrade step done");
            }

            if (step == target) {
                break;
            }
        }

        SystemConfig databaseVersion = systemConfigRepository.findOne(systemStringVersionKey);
        if (databaseVersion == null) {
            throw new InternalError("!!!No Current Version!!!");
        }
        databaseVersion.setValueForCode(String.valueOf(target.ordinal()));
        systemConfigRepository.save(databaseVersion);
    }


}

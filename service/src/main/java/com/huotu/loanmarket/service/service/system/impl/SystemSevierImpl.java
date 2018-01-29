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
import com.huotu.loanmarket.service.service.system.SystemService;
import org.springframework.stereotype.Service;

/**
 * @author guomw
 * @date 29/01/2018
 */
@Service
public class SystemSevierImpl implements SystemService {
    @Override
    public AppSystemVersion checkUpdate(String appVersion, DeviceTypeEnum deviceType) {
        return null;
    }
}

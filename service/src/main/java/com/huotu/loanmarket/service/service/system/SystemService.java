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

/**
 * @author guomw
 * @date 29/01/2018
 */
public interface SystemService {

    /**
     * 检查版本更新
     *
     * @param appVersion app版本号
     * @param deviceType app设备类型
     * @return
     */
    AppSystemVersion checkUpdate(String appVersion, DeviceTypeEnum deviceType);
}

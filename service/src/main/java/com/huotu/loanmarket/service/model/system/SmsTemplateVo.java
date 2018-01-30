/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.model.system;

import lombok.Data;

/**
 *
 * @author guomw
 * @date 2017/12/16
 */
@Data
public class SmsTemplateVo {
    /** tmpl_id */
    private Integer tmplId;

    /**
     * 商户id
     */
    private Integer merchantId;

    /** 发送场景 */
    private Integer sceneType;

    private String sceneTypeName;

    /** 短信内容 */
    private String tmplContent;

    /** 添加时间 */
    private String createtime;

    /** 是否启用 */
    private Integer tmplStatus;
}

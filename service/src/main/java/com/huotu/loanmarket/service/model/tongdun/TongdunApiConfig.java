package com.huotu.loanmarket.service.model.tongdun;

import lombok.Getter;
import lombok.Setter;

/**
 * 同盾接口配置参数
 * @author wm
 */
@Getter
@Setter
public class TongdunApiConfig {
    /**
     * 申请接口地址
     */
    private String submitUrl = "https://apitest.tongdun.cn/preloan/apply/v5";
    /**
     * 查询报告接口地址
     */
    private String queryUrl = "https://apitest.tongdun.cn/preloan/report/v9";
    /**
     * 合作方标识
     */
    private String partnerCode = "ghwl";
    /**
     * 合作方密钥
     */
    private String partnerKey = "ae28ff1a3e424235ad14e40ec88b8938";
    /**
     * 应用名
     */
    private String partnerApp = "ckd_web";
}
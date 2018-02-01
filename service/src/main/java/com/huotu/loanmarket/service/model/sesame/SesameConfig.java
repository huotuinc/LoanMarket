package com.huotu.loanmarket.service.model.sesame;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author hxh
 * @Date 2018/1/30 17:32
 */
@Getter
@Setter
public class SesameConfig {
    /**
     * API 账户 商汤相关
     */
    private String apiId;
    /**
     * API密钥 商汤相关
     */
    private String apiSecret;
    /**
     * 芝麻应用标识（行业黑名单）
     */
    private String appId;
    /**
     * 商家私钥（行业黑名单）
     */
    private String privateKey;
    /**
     * 芝麻公钥（行业黑名单）
     */
    private String publicKey;
    /**
     * 芝麻应用标识（欺诈信息验证）
     */
    private String appCheatId;
    /**
     * 芝麻公钥（欺诈信息验证）
     */
    private String privateCheatKey;
    /**
     * 芝麻私钥（欺诈信息验证）
     */
    private String publicCheatKey;

}

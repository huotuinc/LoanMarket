package com.huotu.loanmarket.service.model.tongdun.report;

import lombok.Getter;
import lombok.Setter;

/**
 * 归属地解析
 * @author wm
 */
@Getter
@Setter
public class AddressDetect {
    /**
     * 身份证所属地
     */
    private String id_card_address;
    /**
     * ip所属地
     */
    private String true_ip_address;
    /**
     * wifi所属地
     */
    private String wifi_address;
    /**
     * 基站所属地
     */
    private String cell_address;
    /**
     * 银行卡所属地
     */
    private String bank_card_address;
    /**
     * 手机所属地
     */
    private String mobile_address;
}
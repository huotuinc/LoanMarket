package com.huotu.loanmarket.service.model.tongdun.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wm
 */
@Getter
@Setter
public class PlatformDetailDimension {
    /**
     * 维度命中多头个数
     */
    private  Integer count;
    /**
     *维度命中多头详情，格式如：格式如：["信贷理财:1","P2P网贷:2"]
     */
    private List<String> detail;
    /**
     *维度展示名
     */
    private String dimension;
}
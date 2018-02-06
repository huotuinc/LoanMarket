package com.huotu.loanmarket.service.model.tongdun.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 频度详情
 * @author wm
 */
@Getter
@Setter
public class FrequencyDetail {
    private String detail;
    private List<String> data;
}
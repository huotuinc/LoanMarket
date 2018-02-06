package com.huotu.loanmarket.service.model.tongdun.report;

import java.util.Comparator;

/**
 * 风险分组名排序器
 * @author wm
 */
public class RiskGroupComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        Integer n1 = ReportConstant.DEFAULT_GROUP_NAME_MAP.containsKey(o1)?ReportConstant.DEFAULT_GROUP_NAME_MAP.get(o1):99;
        Integer n2 = ReportConstant.DEFAULT_GROUP_NAME_MAP.containsKey(o2)?ReportConstant.DEFAULT_GROUP_NAME_MAP.get(o2):99;
        return  n1-n2;
    }
}
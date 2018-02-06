package com.huotu.loanmarket.service.model.tongdun.report;

import java.util.HashMap;
import java.util.Map;

/**
 * 报告中用到的常量
 * @author wm
 */
public class ReportConstant {
    public static final Map<String,Integer> DEFAULT_GROUP_NAME_MAP = new HashMap<String , Integer>(){{
        put("个人基本信息核查", 1);
        put("风险信息扫描", 2);
        put("多平台借贷申请检测", 3);
        put("关联人信息扫描", 4);
        put("客户行为检测", 5);
    }};
}
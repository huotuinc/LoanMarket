package com.huotu.loanmarket.service.model.tongdun;

import lombok.Getter;
import lombok.Setter;


/**
 * 同盾日志后台搜索条件实体
 * @author wm
 */
@Getter
@Setter
public class TongdunLogSearchCondition {
    /**
     * 商家id
     */
    private Long merchantId;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 判定结果，对应枚举TongdunEnum.DecisionType
     */
    private Integer decision;
    /**
     * 最小分数
     */
    private Integer minScore;
    /**
     * 最大分数
     */
    private Integer maxScore;
    /**
     * 搜索类型，对应枚举对应枚举TongdunEnum.LogSearchType
     */
    private Integer searchType;
    /**
     * 关键字
     */
    private String searchKey;
    /**
     * 是否调用成功
     */
    private Integer state;
    private int pageIndex = 1;

    private int pageSize = 20;
}
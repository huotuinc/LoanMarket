package com.huotu.loanmarket.service.searchable;

import lombok.Data;

/**
 * @author allan
 * @date 23/10/2017
 */
@Data
public class ProjectSearchCondition {
    /**
     * 0：全部，4：top4条
     * 这个参数大于0时不再进行分组
     */
    private int topNum;

    /**
     * 是否热卖
     */
    private int isHot = -1;

    /**
     * 是否新品推荐
     */
    private int isNew = -1;

    /**
     * 分类
     */
    private int categoryId;

    /**
     * 最高可待金额
     */
    private double loanMoney;

    private String name;
}

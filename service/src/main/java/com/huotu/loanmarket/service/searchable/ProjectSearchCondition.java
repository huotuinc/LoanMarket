package com.huotu.loanmarket.service.searchable;

import lombok.Data;

/**
 * Created by allan on 23/10/2017.
 */
@Data
public class ProjectSearchCondition {
    /**
     * 0：全部，4：top4条
     */
    private int topNum;

    private int isHot = -1;

    private int isNew = -1;

    private int categoryId;

    private double loanMoney;

    private int pageIndex = 1;
}

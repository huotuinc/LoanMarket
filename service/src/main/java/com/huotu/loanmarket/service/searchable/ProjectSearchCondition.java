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
}

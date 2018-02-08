package com.huotu.loanmarket.service.service.system;

import com.huotu.loanmarket.common.Constant;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author helloztt
 */
@Data
public class DataStatisticsSearcher {
    private Integer merchantId;
    private LocalDate beginDate;
    private LocalDate endDate;
    private int pageNo = 1;
    private int pageSize = Constant.PAGE_SIZE;
}

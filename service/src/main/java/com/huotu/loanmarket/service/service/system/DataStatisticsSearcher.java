package com.huotu.loanmarket.service.service.system;

import com.huotu.loanmarket.common.Constant;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author helloztt
 */
@Data
public class DataStatisticsSearcher {
    private Integer merchantId = Constant.MERCHANT_ID;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate beginDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    private int pageIndex = 1;
    private int pageSize = Constant.PAGE_SIZE;
}

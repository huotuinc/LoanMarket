package com.huotu.loanmarket.service.model.user;

import com.huotu.loanmarket.common.Constant;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author helloztt
 */
@Data
public class UserSearcher {

    private Long userId;
    private String userName;
    private Long merchantId;
    /**
     * 注册时间区间
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createBeginTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createEndTime;

    private int pageIndex = 1;
    private int pageSize = Constant.PAGE_SIZE;
}

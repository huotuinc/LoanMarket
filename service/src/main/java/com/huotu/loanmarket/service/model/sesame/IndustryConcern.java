package com.huotu.loanmarket.service.model.sesame;

import com.huotu.loanmarket.service.enums.SesameEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @Author hxh
 * @Date 2018/1/31 17:35
 */
@Getter
@Setter
public class IndustryConcern {
    /**
     * 风险信息行业编码
     */
    private SesameEnum biz_code;
    /**
     * 等级编号
     */
    private String code;
    /**
     * 等级
     */
    private String level;
    /**
     * 数据刷新时间
     */
    private LocalDateTime refresh_time;
    /**
     * 结清状态
     */
    private boolean settlement;
    /**
     * 用户本人对该条负面记录有异议时，表示该异议处理流程的状态
     */
    private String status;
    /**
     * 当用户进行异议处理，并核查完毕之后，仍有异议时，给出的声明
     */
    private String statement;
}

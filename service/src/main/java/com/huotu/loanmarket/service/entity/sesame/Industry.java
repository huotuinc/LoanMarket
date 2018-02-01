package com.huotu.loanmarket.service.entity.sesame;

import com.huotu.loanmarket.service.enums.SesameEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @Author hxh
 * @Date 2018/2/1 11:56
 */
@Entity
@Table(name = "zx_industry")
@Getter
@Setter
public class Industry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    /**
     * 订单编号
     */
    @Column(name = "order_id")
    private String orderId;
    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 风险信息行业编码
     */
    @Column(name = "biz_code")
    private SesameEnum.IndustryType biz_code;
    /**
     * 行业名单风险类型
     */
    private SesameEnum.IndustryType type;
    /**
     * 等级编号
     */
    private SesameEnum.RankNumber code;
    /**
     * 等级
     */
    private SesameEnum.Level level;
    /**
     * 数据刷新时间
     */
    private LocalDateTime refresh_time;
    /**
     * 结清状态
     * T 当前不逾期
     * F 当前逾期
     * 空值 未知
     */
    private boolean settlement;
    /**
     * 用户本人对该条负面记录有异议时，表示该异议处理流程的状态
     */
    private SesameEnum.Status status;
    /**
     * 当用户进行异议处理，并核查完毕之后，仍有异议时，给出的声明
     */
    private String statement;
}

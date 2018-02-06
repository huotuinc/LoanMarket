package com.huotu.loanmarket.service.entity.tongdun;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户同盾校验报告
 * @author wm
 */
@Table(name = "zx_tongdun_req_log")
@Entity
@Getter
@Setter
public class TongdunRequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_id",length = 40)
    private String orderId;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 申请时间
     */
    @Column(name = "apply_time", columnDefinition = "datetime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime applyTime;
    /**
     * 请求状态
     */
    @Column(name = "state")
    private Integer state;
    /**
     * 申请时的错误代码
     */
    @Column(name = "submit_reason_code",length = 30)
    private String submitReasonCode;
    /**
     * 申请时的错误描述
     */
    @Column(name = "submit_reason_desc",length = 400)
    private String submitReasonDesc;
    /**
     * 报告id
     */
    @Column(name = "report_id",length = 30)
    private String reportId;
    /**
     * 报告时间
     */
    @Column(name = "report_time",columnDefinition = "datetime")
    private LocalDateTime reportTime;
    /**
     * 分数
     */
    @Column(name = "final_score")
    private Integer finalScore;
    /**
     * 判定结果
     */
    @Column(name = "final_decision",length = 20)
    private String finalDecision;
    /**
     * 申请时的参数
     */
    @Column(name = "submit_params",columnDefinition="text")
    private String submitParams;
    /**
     * 报告完整内容
     */
    @Column(name = "report_content",columnDefinition="text")
    private String reportContent;
    /**
     * 商家id
     */
    @Column(name = "merchant_id")
    private Long merchantId;
    /**
     * 应用id
     */
    @Column(name = "application_id",length = 40)
    private String applicationId;
    /**
     * 异常信息
     */
    @Column(name = "exception",columnDefinition="text")
    private String exception;

    @Column(name = "id_number",length = 20)
    private String idNumber;

    @Column(name = "mobile",length = 11)
    private String mobile;

    @Column(name = "name",length = 25)
    private String name;
}
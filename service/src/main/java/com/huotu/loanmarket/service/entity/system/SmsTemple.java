package com.huotu.loanmarket.service.entity.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 短信模板
 * Created by hot on 2017/11/21.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_sms_tmpl")
public class SmsTemple {
    /** tmpl_id */
    @Id
    @Column(name = "tmpl_id", unique = true, nullable = false, length = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tmplId;

    /**
     * 商户id
     */
    @Column(name = "merchant_id")
    private Integer merchantId;

    /** 发送场景 */
    @Column(name = "scene_type",  length = 3)
    private Integer sceneType;

    /** 短信内容 */
    @Column(name = "tmpl_content",  length = 4000)
    private String tmplContent;

    /** 添加时间 */
    @Column(name = "createtime",columnDefinition = "timestamp")
    private LocalDateTime createtime;

    /** 是否启用 */
    @Column(name = "tmpl_status",  length = 3)
    private boolean tmplStatus = true;


}

package com.huotu.loanmarket.service.entity.merchant;

import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 * 商家系统配置参数
 *
 * @author hxh
 * @date 2017-11-30
 */
@Getter
@Setter
@Entity
@Table(name = "zx_merchant_cfg_items")
public class MerchantConfigItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long configId;
    /**
     * 参数编号
     */
    @Column(name = "item_code")
    private String code;
    /**
     * 参数名称
     */
    @Column(name = "item_name")
    private String name;
    /**
     * 参数值
     */
    @Column(name = "item_value",columnDefinition = "text")
    private String value;
    /**
     * 参数类型
     */
    @Column(name = "item_type",columnDefinition = "tinyint")
    private MerchantConfigEnum type;
    /**
     * 排序
     */
    @Column(name = "item_sort")
    private int sort;
    /**
     * 商户编号
     */
    @Column(name = "merchant_id")
    private Integer merchantId;
    /**
     * 创建时间
     */
    @Column(name = "create_time", columnDefinition = "timestamp")
    private LocalDateTime createTime;

    /**
     * 是否显示
     */
    @Transient
    private boolean display=true;
    /**
     * 是否可编辑
     */
    @Transient
    private boolean editAble=true;

}

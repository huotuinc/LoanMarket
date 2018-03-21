package com.huotu.loanmarket.service.entity.system;

import com.huotu.loanmarket.common.Constant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 广告栏目
 */
@Entity
@Getter
@Setter
@Table(name = "zx_advertisement")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商户id
     */
    @Column(name = "merchant_id")
    private Integer merchantId = Constant.MERCHANT_ID;

    /**
     * 标题
     */
    @Column(name = "title", length = 100)
    private String title;
    /**
     * 图片地址
     */
    @Column(name = "image_url", length = 100)
    private String imageUrl;
    /**
     * 跳转地址
     */
    @Column(name = "target_url", length = 200)
    private String targetUrl;
    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;
}

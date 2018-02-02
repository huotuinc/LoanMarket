/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.repository.payment;

import com.huotu.loanmarket.service.entity.payment.PaymentCfg;
import com.huotu.loanmarket.service.enums.OrderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hxh
 * @date 2017-12-12
 */
@Repository
public interface PaymentRepository extends JpaRepository<PaymentCfg, Long> {

    /**
     * 获取支付方式
     *
     * @param merchantId
     * @return
     */
    List<PaymentCfg> findByMerchantIdOrderBySortNumDesc(Integer merchantId);

    /**
     * 得到可用的支付方式
     *
     * @param merchantId
     * @return
     */
    @Query("select p from PaymentCfg as p where p.merchantId=?1 order by p.sortNum desc ")
    List<PaymentCfg> findAvailableList(Integer merchantId);

    PaymentCfg findByPaymentIdAndMerchantId(Long paymentId, Integer merchantId);


    PaymentCfg findByPayTypeAndMerchantId(OrderEnum.PayType type, Integer merchantId);

    /**
     * 查找指定类型的支付配置
     *
     * @param merchantId
     * @param typeList
     * @return
     */
    List<PaymentCfg> findByMerchantIdAndPayTypeIn(Integer merchantId, List<OrderEnum.PayType> typeList);
}

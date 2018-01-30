/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.repository.system;

import com.huotu.loanmarket.service.entity.user.VerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author guomw
 * @date 13/11/2017
 */
@Repository
public interface VerifyCodeRepository extends JpaRepository<VerifyCode, Integer> {

    /**
     * 根据手机号查询验证码
     *
     * @param mobile     手机号
     * @param merchantId 商家编号
     * @return
     */
    @Query("select  v from  VerifyCode  v where v.mobile=?1 and v.merchant.merchantId=?2")
    VerifyCode findByMobileAndMerchantId(String mobile, int merchantId);
}

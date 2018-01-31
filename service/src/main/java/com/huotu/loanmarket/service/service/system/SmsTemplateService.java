/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.system;

import com.huotu.loanmarket.service.entity.system.SmsTemple;
import com.huotu.loanmarket.service.enums.SmsTemplateSceneTypeEnum;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.model.PageListView;
import com.huotu.loanmarket.service.model.system.SmsTemplateVo;

/**
 * 短信模板验证码
 *
 * @author guomw
 * @date 2017/12/15
 */
public interface SmsTemplateService {

    /**
     * 保存
     * @param smsTemple
     * @return
     */
    SmsTemple save(SmsTemple smsTemple);

    /**
     * 查询模板信息
     * @param templateId
     * @return
     */
    SmsTemplateVo findByTemplateId(Integer templateId);

    /**
     * 查询模板信息
     * @param templateId
     * @return
     */
    SmsTemple findOne(Integer templateId);

    /**
     * 根据模板类型获取模板信息
     * @param merchantId
     * @param sceneType
     * @return
     */
    SmsTemple findByMerchantIdAndSceneType(Integer merchantId, SmsTemplateSceneTypeEnum sceneType);

    /**
     * 获取消息数据列表
     *
     * @param messageType 模板类型
     * @param pageIndex 页码
     * @param pageSize 每页数量
     * @return
     */
    PageListView<SmsTemplateVo> findAll(Integer messageType, Integer pageIndex, Integer pageSize);

    /**
     * 发送短信模板消息提醒
     *
     * @param merchantId 商户ID
     * @param mobile     手机号码
     * @param sceneType  场景类型
     * @return
     * @throws ErrorMessageException
     */
    boolean sendTemplateMessage(int merchantId, String mobile, SmsTemplateSceneTypeEnum sceneType) throws ErrorMessageException;


    /**
     * 发送验证码
     *
     * @param merchantId 商户编号
     * @param mobile     手机号
     * @param safecode   安全码
     * @return
     * @throws ErrorMessageException
     */
    boolean sendVerifyCode(int merchantId, String mobile, String safecode) throws ErrorMessageException;


    /**
     * 检查验证码
     *
     * @param merchantId 商家编号
     * @param mobile     用户手机号
     * @param verifyCode 验证码
     * @return
     */
    boolean checkVerifyCode(int merchantId, String mobile, String verifyCode);


}

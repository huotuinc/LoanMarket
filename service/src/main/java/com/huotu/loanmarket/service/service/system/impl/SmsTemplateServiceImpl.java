/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.system.impl;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.utils.CompareUtils;
import com.huotu.loanmarket.service.entity.system.SmsTemple;
import com.huotu.loanmarket.service.entity.user.VerifyCode;
import com.huotu.loanmarket.service.enums.SmsTemplateSceneTypeEnum;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.model.PageListView;
import com.huotu.loanmarket.service.model.system.SmsTemplateVo;
import com.huotu.loanmarket.service.repository.system.SmsTemplateRepository;
import com.huotu.loanmarket.service.repository.system.VerifyCodeRepository;
import com.huotu.loanmarket.service.repository.user.UserRepository;
import com.huotu.loanmarket.service.service.system.SmsTemplateService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guomw
 * @date 2017/12/15
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    private static final Log log = LogFactory.getLog(SmsTemplateServiceImpl.class);

    @Autowired
    private VerifyCodeRepository verifyCodeRepository;
//    @Autowired
//    private MerchantCfgService merchantCfgService;
    @Autowired
    private SmsTemplateRepository templateRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;

    /**
     * 保存
     *
     * @param smsTemple
     * @return
     */
    @Override
    public SmsTemple save(SmsTemple smsTemple) {
        return templateRepository.save(smsTemple);
    }

    /**
     * 获取
     *
     * @param templateId
     * @return
     */
    @Override
    public SmsTemplateVo findByTemplateId(Integer templateId) {
        SmsTemplateVo itemVo = new SmsTemplateVo();
        SmsTemple temple = this.findOne(templateId);
        BeanUtils.copyProperties(temple, itemVo);
        if (temple.getCreatetime() != null) {
            itemVo.setCreatetime(temple.getCreatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            itemVo.setCreatetime("--");
        }
        SmsTemplateSceneTypeEnum typeEnum = EnumHelper.getEnumType(SmsTemplateSceneTypeEnum.class, temple.getSceneType());
        itemVo.setMerchantId(temple.getMerchantId());
        itemVo.setSceneTypeName(typeEnum.getName());
        itemVo.setTmplStatus(temple.isTmplStatus() ? 1 : 0);
        itemVo.setSceneType(typeEnum.getCode());
        return itemVo;
    }

    /**
     * @param templateId
     * @return
     */
    @Override
    public SmsTemple findOne(Integer templateId) {
        return templateRepository.findOne(templateId);
    }

    /**
     * @param merchantId
     * @param sceneType
     * @return
     */
    @Override
    public SmsTemple findByMerchantIdAndSceneType(Integer merchantId, SmsTemplateSceneTypeEnum sceneType) {
        return templateRepository.findByMerchantIdAndSceneType(merchantId, sceneType.getCode());
    }

    /**
     * 获取数据列表
     *
     * @param messageType 模板类型
     * @param pageIndex   页码
     * @param pageSize    每页数量
     * @return
     */
    @Override
    public PageListView<SmsTemplateVo> findAll(Integer messageType, Integer pageIndex, Integer pageSize) {
        List<SmsTemplateVo> listView = new ArrayList<>();
        PageListView<SmsTemplateVo> result = new PageListView<>();

        Specification<SmsTemple> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (messageType != null && messageType >= 0) {
                predicates.add(criteriaBuilder.equal(root.get("sceneType").as(Integer.class), messageType));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Sort sort = new Sort(Sort.Direction.ASC, "tmplId");
        Page<SmsTemple> page = templateRepository.findAll(specification, new PageRequest(pageIndex - 1, pageSize, sort));
        result.setTotalCount(page.getTotalElements());
        result.setPageCount(page.getTotalPages());
        page.forEach(item -> {
            SmsTemplateVo itemVo = new SmsTemplateVo();
            BeanUtils.copyProperties(item, itemVo);
            if (item.getCreatetime() != null) {
                itemVo.setCreatetime(item.getCreatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                itemVo.setCreatetime("--");
            }
            SmsTemplateSceneTypeEnum typeEnum = EnumHelper.getEnumType(SmsTemplateSceneTypeEnum.class, item.getSceneType());
            itemVo.setMerchantId(item.getMerchantId());
            itemVo.setSceneTypeName(typeEnum.getName());
            itemVo.setTmplStatus(item.isTmplStatus() ? 1 : 0);
            itemVo.setSceneType(typeEnum.getCode());
            listView.add(itemVo);
        });

        result.setList(listView);
        return result;
    }

    /**
     * 发送模板消息提醒
     *
     * @param merchantId 商户ID
     * @param mobile     手机号码，多个手机号码，使用英文逗号隔开  示：13525452542,13552524585 (建议1000或1000以上一个包提交，提交的手机不能超过5W个)
     * @param sceneType  发送场景
     * @return
     * @throws ErrorMessageException
     */
    @Override
    public boolean sendTemplateMessage(int merchantId, String mobile, SmsTemplateSceneTypeEnum sceneType) throws ErrorMessageException {
        if (sceneType.equals(SmsTemplateSceneTypeEnum.SMS_VERIFY_CODE)) {
            return sendVerifyCode(merchantId, mobile, "");
        }
        SmsTemple temple = this.findByMerchantIdAndSceneType(merchantId, sceneType);
        if (temple == null || !temple.isTmplStatus()) {
            throw new ErrorMessageException(UserResultCode.SMS_SCENETYPE_NOT_EXIST);
        }
        String content = temple.getTmplContent();
        try {
            if (send(mobile, content, merchantId)) {
                return true;
            }
        } catch (Exception e) {
            log.info("发送模板信息失败---" + e.getMessage());
        }
        return false;
    }

    /**
     * 发送短信验证码
     *
     * @param merchantId   商户编号
     * @param mobile       手机号
     * @param safecode     安全码
     * @return
     * @throws ErrorMessageException
     */
    @Override
    public boolean sendVerifyCode(int merchantId, String mobile, String safecode) throws ErrorMessageException {


        SmsTemple temple = this.findByMerchantIdAndSceneType(merchantId, SmsTemplateSceneTypeEnum.SMS_VERIFY_CODE);
        if (temple == null || !temple.isTmplStatus()) {
            throw new ErrorMessageException(UserResultCode.SMS_SCENETYPE_NOT_EXIST);
        }
        /**
         * content=您好，您的验证码是{code}，有效期{time}分钟。
         */
        String content = temple.getTmplContent();
        String code = RandomStringUtils.randomNumeric(Constant.VERIFY_CODE_LENGTH);
        String message = content.replace("{code}", code).replace("{time}", String.valueOf(Constant.VERIFY_CODE_TIME_LENGTH));
        try {
            VerifyCode verifyCode = verifyCodeRepository.findByMobileAndMerchantId(mobile, merchantId);
            if (verifyCode != null) {
                //判断每个手机的发送间隔时间不能低于1分钟
                if (CompareUtils.compareToDate(verifyCode.getCreateTime(), 60)) {
                    throw new ErrorMessageException("操作太频繁，请稍后再试!");
                }
            }
            if (send(mobile, message, merchantId)) {
                if (verifyCode == null) {
                    verifyCode = new VerifyCode();
                }
                verifyCode.setVerifyCode(String.valueOf(code));
                verifyCode.setMobile(mobile);
                LocalDateTime now = LocalDateTime.now();
                verifyCode.setCreateTime(now);
                verifyCode.setMerchantId(Constant.MERCHANT_ID);
                verifyCode.setUseStatus(false);
                verifyCodeRepository.save(verifyCode);
                return true;
            }
        } catch (Exception e) {
            log.info("验证码信息失败---" + e.getMessage());
            throw new ErrorMessageException(UserResultCode.CODE8.getCode(), e.getMessage());
        }
        throw new ErrorMessageException(UserResultCode.CODE8);
    }

    /**
     * 校验短信验证码
     *
     * @param merchantId 商家编号
     * @param mobile     用户手机号
     * @param verifyCode 验证码
     * @return
     */
    @Override
    public boolean checkVerifyCode(int merchantId, String mobile, String verifyCode) {
        VerifyCode code = verifyCodeRepository.findByMobileAndMerchantId(mobile, merchantId);
        return code != null && !code.isUseStatus() && CompareUtils.compareToDate(code.getCreateTime(), 60 * Constant.VERIFY_CODE_TIME_LENGTH) && code.getVerifyCode().equals(verifyCode);
    }


    /**
     * 发送验证码短信内容
     *
     * @param mobile  手机号码，多个手机号码，使用英文逗号隔开
     * @param message
     * @return
     * @throws Exception
     */
    private boolean send(String mobile, String message, Integer merchantId) throws Exception {
        if (env.acceptsProfiles(Constant.PROFILE_TEST)) {
            return true;
        }
        try {
            //获取短信接口参数
//            Map<String, String> configItem = merchantCfgService.getConfigItem(merchantId);
//            String response = HttpSender.batchSend(configItem.get(ConfigParameter.MessageParameter.URL.getKey()),
//                    configItem.get(ConfigParameter.MessageParameter.ACCOUNT.getKey()),
//                    configItem.get(ConfigParameter.MessageParameter.PASSWORD.getKey()), mobile, message);
//
//            JSONObject jsonObject = JSONObject.parseObject(response);
//            boolean result = jsonObject.getBigInteger("code").intValue() == 0;
//            String msg = MessageFormat.format("手机号码：{0}，结果：{1},发送内容：{2},errorMsg：{3}", mobile, (result ? "发送成功" : "发送失败"),message, jsonObject.getString("errorMsg"));
//            log.info(msg);
//            return result;
        } catch (Exception e) {
            log.error(mobile + "发送信息失败---" + e.getMessage());
        }
        return false;
    }
}

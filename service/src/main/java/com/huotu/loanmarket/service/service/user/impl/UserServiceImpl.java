package com.huotu.loanmarket.service.service.user.impl;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.RandomUtils;
import com.huotu.loanmarket.service.entity.user.Invite;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.entity.user.VerifyCode;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.repository.system.VerifyCodeRepository;
import com.huotu.loanmarket.service.repository.user.InviteRepository;
import com.huotu.loanmarket.service.repository.user.UserRepository;
import com.huotu.loanmarket.service.service.system.SmsTemplateService;
import com.huotu.loanmarket.service.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.time.LocalDateTime;

/**
 * @author hxh
 * @Date 2018/1/30 15:58
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerifyCodeRepository verifyCodeRepository;
    @Autowired
    private SmsTemplateService verifyCodeService;
    @Autowired
    private InviteRepository inviteRepository;


    @Override
    public User findByMerchantIdAndUserId(Integer merchantId, Long userId) {
        return userRepository.findByMerchantIdAndUserId(merchantId, userId);
    }

    /**
     *
     * @param user
     * @param verifyCode
     * @return
     * @throws ErrorMessageException
     */
    @Override
    public User register(User user, String verifyCode) throws ErrorMessageException {
        /**
         * 验证验证码是否有效
         */
        if (verifyCodeService.checkVerifyCode(user.getMerchantId(), user.getUserName(), verifyCode)) {

            //先看看用户是否已经存在
            if (userRepository.countByUserName(user.getUserName()) > 0) {
                throw new ErrorMessageException(UserResultCode.CODE3);
            }


            user.setUserToken(RandomUtils.randomString());
            String inviterName = "";
            //判断当前注册人身份是否是借款人，且判断是否存在邀请人
            if (user.getInviterId() != null
                    && user.getInviterId() > 0) {
                inviterName = userRepository.findUserNameByUserId(user.getInviterId());
            }
            user = userRepository.saveAndFlush(user);
            if (!StringUtils.isEmpty(inviterName)) {
                //添加邀请
                addInviteLog(user.getUserId(), user.getUserName(), user.getRealName(), user.getInviterId(), inviterName);
            }


            /**
             * 设置验证码使用状态
             */
            VerifyCode code = verifyCodeRepository.findByMobileAndMerchantId(user.getUserName(), Constant.MERCHANT_ID);
            code.setUseStatus(true);

            return user;
        }
        throw new ErrorMessageException(UserResultCode.CODE9);
    }

    /**
     * 添加邀请日志
     *
     * @param userId        用户ID
     * @param userName      用户名
     * @param realName      用户姓名
     * @param inviterUserId 邀请者ID
     */
    private void addInviteLog(Long userId, String userName, String realName, Long inviterUserId, String inviterName) {
       try {
           //添加邀请
           Invite invite = new Invite();
           invite.setMerchantId(Constant.MERCHANT_ID);
           invite.setUserId(userId);
           invite.setUserName(userName);
           invite.setRealName(realName);
           invite.setInviterId(inviterUserId);
           invite.setTime(LocalDateTime.now());
           if (inviterName != null && !StringUtils.isEmpty(inviterName)) {
               invite.setInviter(inviterName);
               inviteRepository.save(invite);
           }
       }
       catch (Exception ex){
            log.error(ex);
       }
    }
}

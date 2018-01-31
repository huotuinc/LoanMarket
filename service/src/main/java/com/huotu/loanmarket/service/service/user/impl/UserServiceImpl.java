package com.huotu.loanmarket.service.service.user.impl;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.RandomUtils;
import com.huotu.loanmarket.common.utils.RequestUtils;
import com.huotu.loanmarket.common.utils.StringUtilsExt;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
     * 注册
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
        if (verifyCodeService.checkVerifyCode(user.getUserName(), verifyCode)) {

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
            VerifyCode code = verifyCodeRepository.findByMobileAndMerchantId(user.getUserName());
            code.setUseStatus(true);

            return user;
        }
        throw new ErrorMessageException(UserResultCode.CODE9);
    }

    /**
     * 登录
     *
     * @param loginName
     * @param loginPassword 密码(md5)或验证码
     * @param loginType     登录方式[0:密码登录 1:验证码登录]
     * @param request
     * @return
     * @throws ErrorMessageException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User login(String loginName, String loginPassword,
                      @RequestParam(required = false, defaultValue = "0") int loginType,
                      @RequestParam(required = false) HttpServletRequest request) throws ErrorMessageException {

        if (loginType == 1) {
            if (!verifyCodeService.checkVerifyCode(loginName, loginPassword)) {
                throw new ErrorMessageException(UserResultCode.CODE9);
            }
        }

        User userInfo = userRepository.findByUserName(loginName);
        if (userInfo == null) {
            throw new ErrorMessageException(UserResultCode.CODE5);
        }

        //是否删除 是否锁定
        if (userInfo.isDisabled() || userInfo.isLocked()) {
            throw new ErrorMessageException(UserResultCode.CODE7);
        }

        if (loginType == 0) {
            if (!userInfo.getPassword().equalsIgnoreCase(loginPassword)) {
                throw new ErrorMessageException(UserResultCode.CODE6);
            }
        } else {
            VerifyCode code = verifyCodeRepository.findByMobileAndMerchantId(loginName);
            code.setUseStatus(true);
        }
        userInfo.setUserToken(RandomUtils.randomString());
        userInfo.setLastLoginTime(LocalDateTime.now());
        userInfo.setLoginCount(userInfo.getLoginCount() + 1);
        if (request != null) {
            userInfo.setLastLoginIp(StringUtilsExt.getClientIp(request));
            userInfo.setAppVersion(RequestUtils.getHeader(request, Constant.APP_VERSION_KEY));
            userInfo.setOsType(RequestUtils.getHeader(request, Constant.APP_SYSTEM_TYPE_KEY));
            userInfo.setOsVersion(RequestUtils.getHeader(request, Constant.APP_OS_VERSION_KEY));
            userInfo.setMobileType(RequestUtils.getHeader(request, Constant.APP_MOBILE_TYPE_KEY));
            userInfo.setEquipmentId(RequestUtils.getHeader(request, Constant.APP_EQUIPMENT_NUMBER_KEY));
        }
        return userInfo;
    }


    /**
     * 修改最后登录时间
     *
     * @param userId
     */
    @Override
    public void updateLastLoginTime(long userId) {
        userRepository.updateLastLoginTime(userId, LocalDateTime.now());
    }

    /**
     * 修改、忘记密码
     *
     * @param username
     * @param newPassword
     * @param verifyCode
     * @return
     * @throws ErrorMessageException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(String username, String newPassword, String verifyCode) throws ErrorMessageException {
        /**
         * 检查验证码是否有效
         */
        if (!verifyCodeService.checkVerifyCode(username, verifyCode)) {
            throw new ErrorMessageException(UserResultCode.CODE9);
        }

        User myUser = userRepository.findByUserName(username);

        if (myUser == null) {
            throw new ErrorMessageException(UserResultCode.CODE5);
        }
        myUser.setPassword(newPassword);

        /**
         * 设置验证码使用状态
         */
        VerifyCode code = verifyCodeRepository.findByMobileAndMerchantId(myUser.getUserName());
        code.setUseStatus(true);

        return true;
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
        } catch (Exception ex) {
            log.error(ex);
        }
    }
}

package com.huotu.loanmarket.service.service.user;

import com.huotu.loanmarket.service.aop.BusinessSafe;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author hxh
 * @Date 2018/1/30 15:57
 */
public interface UserService {
    /**
     * 查询
     *
     * @param merchantId
     * @param userId
     * @return
     */
    User findByMerchantIdAndUserId(Integer merchantId, Long userId);


    /**
     * 用户注册
     *
     * @param user
     * @param verifyCode
     * @return
     * @throws ErrorMessageException
     */
    @BusinessSafe
    @Transactional(rollbackFor = Exception.class)
    User register(User user, String verifyCode) throws ErrorMessageException;

    /**
     * 登录
     *
     * @param loginName
     * @param loginPassword 密码(md5)或验证码
     * @param loginType 登录方式[0:密码登录 1:验证码登录]
     * @param request
     * @return
     * @throws  ErrorMessageException
     */
    @Transactional(rollbackFor = Exception.class)
    User login(String loginName, String loginPassword,@RequestParam(required = false,defaultValue = "0") int loginType, @RequestParam(required = false) HttpServletRequest request) throws ErrorMessageException;;

    /**
     * 修改最后登录时间
     *
     * @param userId
     */
    void updateLastLoginTime(long userId);

    /**
     * 修改、忘记密码
     *
     * @param username
     * @param newPassword
     * @param verifyCode
     * @return
     * @throws ErrorMessageException
     */
    @Transactional(rollbackFor = Exception.class)
    boolean updatePassword(String username, String newPassword, String verifyCode) throws ErrorMessageException;

}

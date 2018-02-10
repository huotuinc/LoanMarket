package com.huotu.loanmarket.service.service.user;

import com.huotu.loanmarket.service.aop.BusinessSafe;
import com.huotu.loanmarket.service.entity.user.Invite;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.model.PageListView;
import com.huotu.loanmarket.service.model.user.UserInviteVo;
import com.huotu.loanmarket.service.model.user.UserListVo;
import com.huotu.loanmarket.service.model.user.UserSearcher;
import org.springframework.data.domain.Page;
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
     * @param loginType     登录方式[0:密码登录 1:验证码登录]
     * @param request
     * @return
     * @throws ErrorMessageException
     */
    @Transactional(rollbackFor = Exception.class)
    User login(String loginName, String loginPassword, @RequestParam(required = false, defaultValue = "0") int loginType, @RequestParam(required = false) HttpServletRequest request) throws ErrorMessageException;

    ;

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

    /**
     * 检查用户token
     *
     * @param merchantId
     * @param userId
     * @param userToken
     * @return
     */
    boolean checkLoginToken(int merchantId, Long userId, String userToken);

    /**
     * 获取邀请数
     *
     * @param userId        用户ID
     * @param isAuthSuccess 认证是否成功
     * @return
     */
    Long countByMyInvite(Long userId, boolean isAuthSuccess);

    /**
     * 获取我的邀请列表
     *
     * @param userId
     * @param isAuthSuccess
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PageListView<UserInviteVo> getMyInviteList(Long userId, boolean isAuthSuccess, boolean mobileSafe, int pageIndex, int pageSize);

    /**
     * 管理后台 用户列表
     *
     * @param userSearcher
     * @return
     */
    Page<UserListVo> getUserList(UserSearcher userSearcher);

    /**
     * 保存
     *
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    User save(User user);

    /**
     * 保存用户头像
     *
     * @param userId
     * @param imgUrl
     */
    void setHeadImg(Long userId, String imgUrl);
}

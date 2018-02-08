package com.huotu.loanmarket.service.repository.user;

import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guomw
 * @Author hxh
 * @Date 2018/1/30 16:02
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * 查询
     *
     * @param merchantId
     * @param userId
     * @return
     */
    @Query("select u from User u where u.merchantId=?1 and u.userId=?2")
    User findByMerchantIdAndUserId(Integer merchantId, Long userId);

    /**
     * 判断用户是否存在，大于0，则存在
     *
     * @param userName
     * @return
     */
    @Query("select count(u) from User u where u.userName=?1")
    long countByUserName(String userName);

    /**
     * 根据用户ID查找用户名
     *
     * @param userId
     * @return
     */
    @Query("select u.userName from User u where u.userId=?1")
    String findUserNameByUserId(Long userId);

    /**
     * 根据用户名获取数据
     *
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * 更新用户的最近登录时间
     *
     * @param userId    用户编号
     * @param loginTime
     * @return
     */
    @Query("update User u set u.lastLoginTime = ?2 where u.userId = ?1")
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = RuntimeException.class)
    int updateLastLoginTime(long userId, LocalDateTime loginTime);

    /**
     * 更新用户认证状态
     *
     * @param userId      用户id
     * @param statusEnums 认证状态
     * @return
     */
    @Query("update User u set u.authStatus = ?2 where u.userId = ?1")
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackFor = RuntimeException.class)
    int updateAuthStatus(long userId, UserAuthorizedStatusEnums statusEnums);

    /**
     * 统计一批用户的邀请数量
     *
     * @param userIdList 邀请人id列表
     * @return
     */
    @Query("select u.inviterId,count(u.inviterId) from User u where u.inviterId in ?1 group by u.inviterId")
    List<Object[]> countByInviterId(List<Long> userIdList);

    /**
     * 根据注册时间统计用户数量
     *
     * @param merchantId   商户ID
     * @param regBeginTime 注册起始时间
     * @param regEndTime   注册结束时间
     * @return
     */
    @Query("select count(u.userId) from User u where u.merchantId = ?1 and u.regTime >= ?2 and u.regTime < ?3")
    int countByMerchantIdAndRegTime(Integer merchantId, LocalDateTime regBeginTime, LocalDateTime regEndTime);
}

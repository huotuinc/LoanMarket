package com.huotu.loanmarket.service.repository.user;

import com.huotu.loanmarket.service.entity.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author guomw
 * @Author hxh
 * @Date 2018/1/30 16:02
 */
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    /**
     * 查询
     *
     * @param merchantId
     * @param userId
     * @return
     */
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

}

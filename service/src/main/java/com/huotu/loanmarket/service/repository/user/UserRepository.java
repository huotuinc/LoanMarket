package com.huotu.loanmarket.service.repository.user;

import com.huotu.loanmarket.service.entity.user.User;
import com.sun.tools.corba.se.idl.StringGen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

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
    long countByUserName(String userName);

    /**
     * 根据用户ID查找用户名
     *
     * @param userId
     * @return
     */
    @Query("select u.userName from User u where u.userId=?1")
    String findUserNameByUserId(Long userId);

}

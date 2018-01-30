package com.huotu.loanmarket.service.repository.user;

import com.huotu.loanmarket.service.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author hxh
 * @Date 2018/1/30 16:02
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 查询
     *
     * @param merchantId
     * @param userId
     * @return
     */
    User findByMerchantIdAndUserId(Integer merchantId, Long userId);
}

package com.huotu.loanmarket.service.repository.sesame;

import com.huotu.loanmarket.service.entity.sesame.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author hxh
 * @Date 2018/2/1 14:17
 */
public interface IndustryRepository extends JpaRepository<Industry, Integer> {
    /**
     * 查询
     *
     * @param userId
     * @param orderId
     * @return
     */
    List<Industry> findByUserIdAndOrderId(Long userId, String orderId);
}

package com.huotu.loanmarket.service.service.sesame;

import com.huotu.loanmarket.service.entity.sesame.Industry;

import java.util.List;

/**
 * @Author hxh
 * @Date 2018/1/30 16:17
 */
public interface SesameService {

    /**
     * 保存
     *
     * @param industry
     * @return
     */
    Industry save(Industry industry);

    /**
     * 查询
     *
     * @param userId
     * @param orderId
     * @return
     */
    List<Industry> findByUserIdAndOrderId(Long userId, String orderId);
}

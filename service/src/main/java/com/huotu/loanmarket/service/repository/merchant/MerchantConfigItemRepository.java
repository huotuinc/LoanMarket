package com.huotu.loanmarket.service.repository.merchant;

import com.huotu.loanmarket.service.entity.merchant.MerchantConfigItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author hxh
 * @Date 2018/1/30 17:28
 */
public interface MerchantConfigItemRepository extends JpaRepository<MerchantConfigItem,Long>{
    /**
     * 根据商户编号查询
     *
     * @param merchantId
     * @return
     */
    List<MerchantConfigItem> findByMerchantId(int merchantId);
}

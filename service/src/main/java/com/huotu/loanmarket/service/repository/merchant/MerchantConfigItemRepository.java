package com.huotu.loanmarket.service.repository.merchant;

import com.huotu.loanmarket.service.entity.merchant.MerchantConfigItem;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author hxh
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

    /**
     * 根据商户编号和类型查询
     * @param merchantId
     * @param type
     * @return
     */
    List<MerchantConfigItem> findByMerchantIdAndType(int merchantId, MerchantConfigEnum type);

}

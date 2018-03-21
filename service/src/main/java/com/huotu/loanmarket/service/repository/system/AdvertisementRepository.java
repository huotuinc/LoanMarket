package com.huotu.loanmarket.service.repository.system;

import com.huotu.loanmarket.service.entity.system.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement,Long>,JpaSpecificationExecutor<Advertisement> {

    @Query("select a from Advertisement  a where a.merchantId=?1 order by a.sort")
    List<Advertisement> findByMerchantId(Integer merchantId);
}

package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanEquipment;
import com.huotu.loanmarket.service.repository.LoanEquipmentRepository;
import com.huotu.loanmarket.service.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class EquipmentServiceImpl extends AbstractCrudService<LoanEquipment, Integer> implements EquipmentService {

    @Autowired
    public EquipmentServiceImpl(LoanEquipmentRepository repository) {
        super(repository);
    }

    @Override
    public LoanEquipment saveAppInfo(String appVersion, String osVersion, String osType) {
        LoanEquipment loanEquipment = new LoanEquipment();
        loanEquipment.setAppVersion(appVersion);
        loanEquipment.setOsType(osType);
        loanEquipment.setOsVersion(osVersion);
        return this.repository.saveAndFlush(loanEquipment);
    }
}

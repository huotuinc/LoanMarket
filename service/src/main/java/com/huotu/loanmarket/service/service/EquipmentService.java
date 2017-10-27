package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.service.base.CrudService;
import com.huotu.loanmarket.service.entity.LoanEquipment;

/**
 *
 * @author hxh
 * @date 2017-10-27
 */
public interface EquipmentService extends CrudService<LoanEquipment, Integer> {
    LoanEquipment saveAppInfo(String appVersion, String osVersion, String osType);
}

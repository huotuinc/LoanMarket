package com.huotu.loanmarket.web.controller.config;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.merchant.MerchantConfigItem;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
import com.huotu.loanmarket.service.service.merchant.MerchantCfgService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/config")
public class ConfigController {
    private static final Log log = LogFactory.getLog(ConfigController.class);
    @Autowired
    private MerchantCfgService merchantCfgService;

    /**
     * 系统参数配置
     *
     * @param type 参数类型
     * @return
     */
    @RequestMapping(value = "/systemConfig", method = RequestMethod.GET)
    public String authentication(@RequestParam(required = false, defaultValue = "0") int type, Model model) {
        List<MerchantConfigItem> configItems = merchantCfgService.findByType(type);
        MerchantConfigEnum[] configEnums = MerchantConfigEnum.values();
        MerchantConfigEnum configEnum = EnumHelper.getEnumType(MerchantConfigEnum.class, type);
        model.addAttribute("configItems", configItems);
        model.addAttribute("configEnum", configEnum);
        model.addAttribute("configEnums", configEnums);
        return "config/merchant_config";
    }

    @RequestMapping(value = "/configEdit/{configId}", method = RequestMethod.GET)
    public String editConfig(@PathVariable long configId, Model model) {
        MerchantConfigItem configItem = new MerchantConfigItem();
        configItem.setMerchantId(Constant.MERCHANT_ID);
        boolean status = false;
        if (configId > 0) {
            configItem = merchantCfgService.findByConfigIdAndMerchantId(configId, Constant.MERCHANT_ID);
            status = true;
        }
        model.addAttribute("configItem", configItem);
        if (status) {
            model.addAttribute("configEnum", configItem.getType());
        } else {
            //添加
            MerchantConfigEnum[] configEnums = MerchantConfigEnum.values();
            model.addAttribute("configEnums", configEnums);
        }
        model.addAttribute("status", status);
        return "config/merchant_config_edit";
    }

    @RequestMapping(value = "/configDetailEdit", method = RequestMethod.GET)
    public String editConfig(MerchantConfigItem merchantConfigItem, Integer configType, Model model) {
        merchantConfigItem.setMerchantId(Constant.MERCHANT_ID);
        MerchantConfigEnum enumType = EnumHelper.getEnumType(MerchantConfigEnum.class, configType);
        merchantConfigItem.setType(enumType);
        model.addAttribute("configItem", merchantConfigItem);
        model.addAttribute("configEnum", merchantConfigItem.getType());
        model.addAttribute("status", true);
        return "config/merchant_config_edit";
    }

    @RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult save(MerchantConfigItem configItem, int configType) {
        MerchantConfigEnum type = EnumHelper.getEnumType(MerchantConfigEnum.class, configType);
        configItem.setType(type);
        configItem.setMerchantId(Constant.MERCHANT_ID);
        return merchantCfgService.save(configItem);
    }

    @RequestMapping(value = "/configDelete/{configId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult delete(@PathVariable long configId) {
        merchantCfgService.delete(configId);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

}

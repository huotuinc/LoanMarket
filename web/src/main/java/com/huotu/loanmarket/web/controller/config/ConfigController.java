package com.huotu.loanmarket.web.controller.config;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.merchant.MerchantConfigItem;
import com.huotu.loanmarket.service.entity.system.Advertisement;
import com.huotu.loanmarket.service.entity.system.CheckConfig;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
import com.huotu.loanmarket.service.repository.system.AdvertisementRepository;
import com.huotu.loanmarket.service.repository.system.CheckConfigRepository;
import com.huotu.loanmarket.service.service.merchant.MerchantCfgService;
import com.huotu.loanmarket.service.service.upload.StaticResourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin/config")
public class ConfigController {
    private static final Log log = LogFactory.getLog(ConfigController.class);
    @Autowired
    private MerchantCfgService merchantCfgService;

    @Autowired
    private CheckConfigRepository checkConfigRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private StaticResourceService staticResourceService;

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


    @RequestMapping(value = "/checkConfig", method = RequestMethod.GET)
    public String checkConfig(Model model) {

        CheckConfig checkConfig = checkConfigRepository.findOne(Constant.MERCHANT_ID);
        if (checkConfig == null) {
            checkConfig = new CheckConfig();
            checkConfig.setBlackListCheck(0L);
            checkConfig.setElectronicBusinessCheck(0L);
            checkConfig.setOperatorCheck(0L);
        }
        model.addAttribute("data", checkConfig);
        return "config/check_config";
    }

    @RequestMapping(value = "/checkConfigDo", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult checkConfigDo(Long blackListCheck, Long operatorCheck, Long electronicBusinessCheck) {
        CheckConfig checkConfig = checkConfigRepository.findOne(Constant.MERCHANT_ID);
        if (checkConfig != null) {
            checkConfig.setBlackListCheck(blackListCheck);
            checkConfig.setOperatorCheck(operatorCheck);
            checkConfig.setElectronicBusinessCheck(electronicBusinessCheck);
            checkConfigRepository.saveAndFlush(checkConfig);
        } else {
            checkConfig = new CheckConfig();
            checkConfig.setBlackListCheck(blackListCheck);
            checkConfig.setOperatorCheck(operatorCheck);
            checkConfig.setElectronicBusinessCheck(electronicBusinessCheck);
            checkConfigRepository.saveAndFlush(checkConfig);
        }
        return ApiResult.resultWith(AppCode.SUCCESS);
    }


    @RequestMapping(value = "/advertisementList", method = RequestMethod.GET)
    public String advertisementList(Model model) {
        model.addAttribute("list", advertisementRepository.findByMerchantId(Constant.MERCHANT_ID));
        return "config/advertisement_list";
    }

    @RequestMapping(value = "/advertisementConfig", method = RequestMethod.GET)
    public String advertisementConfig(Long id, Model model) {
        Advertisement advertisement;
        if (id > 0) {
            advertisement = advertisementRepository.findOne(id);
        } else {
            advertisement = new Advertisement();
            advertisement.setId(0L);
            advertisement.setImageUrl("");
            advertisement.setSort(0);
            advertisement.setTargetUrl("");
            advertisement.setTitle("");
        }
        model.addAttribute("data", advertisement);
        try {
            model.addAttribute("imageUrlFull", !StringUtils.isEmpty(advertisement.getImageUrl()) ? staticResourceService.getResource(advertisement.getImageUrl()).toString() : "");
        } catch (URISyntaxException e) {
            model.addAttribute("imageUrlFull", "");
        }
        return "config/advertisement_config";
    }

    @RequestMapping(value = "/advertisementConfigDo", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult advertisementConfigDo(Long id, String title, String imageUrl, String targetUrl, Integer sort) {
        if (id > 0) {
            Advertisement advertisement = advertisementRepository.findOne(id);
            advertisement.setTitle(title);
            advertisement.setTargetUrl(targetUrl);
            advertisement.setImageUrl(imageUrl);
            advertisement.setSort(sort);
            advertisementRepository.saveAndFlush(advertisement);
        } else {
            Advertisement advertisement = new Advertisement();
            advertisement.setTitle(title);
            advertisement.setTargetUrl(targetUrl);
            advertisement.setImageUrl(imageUrl);
            advertisement.setSort(sort);
            advertisement.setMerchantId(Constant.MERCHANT_ID);
            advertisementRepository.saveAndFlush(advertisement);
        }
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @RequestMapping(value = "/advertisementDelete", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult advertisementDelete(Long id) {
        advertisementRepository.delete(id);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }


    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult uploadImg(@RequestParam(value = "btnFile", required = false) MultipartFile files) throws IOException {
        String filename = files.getOriginalFilename();
        if (!staticResourceService.typeIsAllow(filename)) {
            return ApiResult.resultWith(AppCode.PARAMETER_ERROR, "图片格式有误");
        }


        String imgPathFront = StaticResourceService.AD_IMG + UUID.randomUUID().toString().replace("-", "") + staticResourceService.getSuffix(filename);
        try {
            URI uri = staticResourceService.uploadResource(imgPathFront, files.getInputStream());
            Map<Object, Object> responseData = new HashMap<>();
            responseData.put("fileUrl", uri);
            responseData.put("fileUri", imgPathFront);
            return ApiResult.resultWith(AppCode.SUCCESS, responseData);
        } catch (URISyntaxException e) {
            log.error("图片保存异常" + e.getMessage());
            return ApiResult.resultWith(AppCode.ERROR, e.getMessage());
        }
    }
}

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.system.AppSystemVersion;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.DeviceTypeEnum;
import com.huotu.loanmarket.service.enums.PackageTypeEnum;
import com.huotu.loanmarket.service.service.system.AppVersionService;
import com.huotu.loanmarket.service.service.system.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * @author guomw
 * @date 2017/12/26
 */
@Controller
@RequestMapping("/version")
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;
    @Autowired
    private SystemService systemService;

    /**
     * 列表页面
     *
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list() {
        return "version/list";
    }

    /**
     * 消息编辑页面
     *
     * @param Id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{Id}",method = RequestMethod.GET)
    public String editPage(@PathVariable Long Id, Model model) {
        AppSystemVersion appVersion = new AppSystemVersion();
        appVersion.setVid(0L);
        appVersion.setVersion("");
        appVersion.setMd5("");
        appVersion.setSize(0);
        appVersion.setUpdateDesc("");
        appVersion.setUpdateType(0);
        appVersion.setDeviceType(DeviceTypeEnum.Android);
        appVersion.setVersionCode(0);
        appVersion.setPackageType(PackageTypeEnum.Simple);
        if (Id != null && Id.intValue() > 0) {
            appVersion = appVersionService.findOne(Id);
        }
        model.addAttribute("data", appVersion);
        return "version/edit";
    }

    /**
     * 列表数据
     *
     * @param updateType
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/listDo")
    @ResponseBody
    public ApiResult listDo(@RequestParam(required = false) Integer updateType,
                            @RequestParam(defaultValue = "1", required = false) int pageIndex,
                            @RequestParam(defaultValue = "10", required = false) int pageSize) {
        pageIndex = pageIndex == 0 ? 1 : pageIndex;
        return ApiResult.resultWith(AppCode.SUCCESS, appVersionService.findAll(updateType, pageIndex, pageSize));
    }


    /**
     * 保存内容
     *
     * @param vid
     * @param versionCode
     * @param version
     * @param updateLink
     * @param md5
     * @param updateType
     * @param content
     * @param size
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ApiResult save(@RequestParam(required = false) Long vid,
                          Integer versionCode,
                          String version,
                          String updateLink,
                          String md5,
                          int updateType,
                          String content,
                          Long size,
                          int packageType,
                          @RequestParam(defaultValue = "1") int deviceType) {
        DeviceTypeEnum deviceTypeEnum = EnumHelper.getEnumType(DeviceTypeEnum.class, deviceType);
        if (deviceTypeEnum == DeviceTypeEnum.Android) {
            if (StringUtils.isEmpty(version)
                    || StringUtils.isEmpty(updateLink)
                    || StringUtils.isEmpty(md5)) {
                return ApiResult.resultWith(AppCode.PARAMETER_ERROR, "请完善信息");
            }
        } else {
            if (StringUtils.isEmpty(version)) {
                return ApiResult.resultWith(AppCode.PARAMETER_ERROR, "请输入版本号");
            }
        }

        AppSystemVersion appVersion = (vid != null && vid > 0) ? appVersionService.findOne(vid) : new AppSystemVersion();
        appVersion.setUpdateType(updateType);
        appVersion.setUpdateDesc(content);
        appVersion.setMd5(md5);
        appVersion.setSize(size);
        appVersion.setUpdateLink(updateLink);
        appVersion.setUpdateType(updateType);
        appVersion.setPackageType(EnumHelper.getEnumType(PackageTypeEnum.class,packageType));
        if (vid == null || vid == 0) {
            appVersion.setVid(0L);
            appVersion.setDeviceType(deviceTypeEnum);
            appVersion.setVersionCode(versionCode);
            appVersion.setVersion(version);
            appVersion.setCreateTime(LocalDateTime.now());
            AppSystemVersion appLastVersion = appVersionService.findLastOne(deviceTypeEnum);
            if (appLastVersion != null) {
                if (appLastVersion.getVersionCode() >= versionCode) {
                    return ApiResult.resultWith(AppCode.PARAMETER_ERROR, "内部版本号必须大于上个版本");
                }
                AppSystemVersion app = systemService.checkUpdate(version, deviceTypeEnum);
                if (app != null || appLastVersion.getVersion().equals(version)) {
                    return ApiResult.resultWith(AppCode.PARAMETER_ERROR, "版本号必须大于上个版本");
                }
            }
        }
        appVersion = appVersionService.save(appVersion);
        return ApiResult.resultWith(AppCode.SUCCESS, appVersion);
    }

    /**
     * 删除版本号
     * @param vid
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ApiResult delete(@RequestParam Long vid) {
        appVersionService.delete(vid);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    /**
     * 更新变包类型
     * @param vid
     * @param packageType
     * @return
     */
    @RequestMapping("/updatePackageType")
    @ResponseBody
    public ApiResult updatePackageType(@RequestParam Long vid,int packageType){
        appVersionService.updatePackageType(vid,EnumHelper.getEnumType(PackageTypeEnum.class,packageType));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

}

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.entity.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huotu.loanmarket.service.enums.DeviceTypeEnum;
import com.huotu.loanmarket.service.enums.PackageTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author guomw
 * @date 29/01/2018
 */

@Getter
@Setter
@Entity
@Table(name = "zx_app_version")
public class AppSystemVersion {
    /**
     * 主键
     */
    @Id
    @Column(name = "version_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vid;

    /**
     * 内部版本号
     */
    @Column(name = "version_code")
    private Integer versionCode;

    /**
     * 版本号
     */
    @Column(name = "version", length = 10)
    private String version;

    /**
     * 更新类型 0普通更新 1=强制更新
     */
    @Column(name = "update_type")
    private int updateType;

    /**
     * 更新连接
     */
    @Column(name = "update_link", length = 200)
    private String updateLink;
    /**
     * 更新内容
     */
    @Column(name = "update_desc")
    private String updateDesc;

    /**
     *
     */
    @Column(name = "md5",length = 32)
    private String md5;

    /**
     * 创建时间
     */
    @Column(name = "create_time", columnDefinition = "timestamp")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * apk文件的大小
     */
    @Column(name="size")
    private long size;

    /**
     * 设备类型
     */
    @Column(name = "device_type",columnDefinition = "tinyint")
    private DeviceTypeEnum deviceType=DeviceTypeEnum.Android;

    /**
     * 包类型，0=变包，1=正常包
     */
    @Column(name = "package_type",columnDefinition = "tinyint")
    private PackageTypeEnum packageType=PackageTypeEnum.Simple;
}

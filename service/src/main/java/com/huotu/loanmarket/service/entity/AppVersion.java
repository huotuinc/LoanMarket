package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "AppVersion")
public class AppVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "versionId")
    private Integer versionId;
    /**
     * 版本id
     */
    @Column(name = "versionCode")
    private Integer versionCode;
    /**
     * 版本名称
     */
    @Column(name = "versionName")
    private String versionName;
    /**
     * 升级说明
     */
    @Column(name = "description")
    private String description;
    /**
     * app下载地址
     */
    @Column(name="appUrl")
    private String appUrl;
}

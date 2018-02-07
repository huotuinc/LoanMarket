package com.huotu.loanmarket.service.model.system;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 分享信息
 * @author wm
 */
@Getter
@Setter
@Builder
public class ShareInfoVo {
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 图标
     */
    private String icon;
    /**
     * 链接
     */
    private String url;
}
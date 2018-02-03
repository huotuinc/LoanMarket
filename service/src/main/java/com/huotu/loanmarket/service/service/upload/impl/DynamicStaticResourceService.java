package com.huotu.loanmarket.service.service.upload.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 动态静态资源处理器
 * <p>请务必设置环境变量</p>
 * <code>huotu.resourcesUri</code>
 * <p>比如<code>http://res.fanmore.cn/</code></p>
 * <code>huotu.resourcesHome</code>
 * <p>比如<code>file://D:/fanmoreresources</code></p>
 * <code>huobanmall.ftpUri</code>
 * <p>比如<code>ftp://userName:password@ftpHost</code></p>
 * <code>huobanmall.resourceUrl</code>
 * <p>比如<code>http://huobanmall.com/</code></p>
 *
 * @author CJ
 */
@Service("resourceService")
@Profile("container")
public class DynamicStaticResourceService extends AbstractStaticResourceService {

    private static final Log log = LogFactory.getLog(DynamicStaticResourceService.class);

    @Autowired
    private void setEnv(Environment env) {
        //104
        String uri = env.getProperty("loan.resourcesUri", (String) null);
        if (uri == null) {
            throw new IllegalStateException("loan.resourcesHome属性");
        }
        String home = env.getProperty("loan.resourcesHome", (String) null);
        if (home == null) {
            throw new IllegalStateException("loan.resourcesUri和loan.resourcesHome属性");
        }
        try {
            this.uriPrefix = new URI(uri);
            this.fileHome = new URI(home);
        } catch (URISyntaxException e) {
            log.error("解析失败", e);
            throw new InternalError("解析失败");
        }
    }
}

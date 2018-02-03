/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2017. All rights reserved.
 */

package com.huotu.loanmarket.service.service.upload.impl;

import com.huotu.loanmarket.service.service.upload.StaticResourceService;
import com.huotu.loanmarket.service.service.upload.VFSHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author guomw
 * @date 16/11/2017
 */
@Service("resourceService")
public abstract class AbstractStaticResourceService implements StaticResourceService {

    private static final Log log = LogFactory.getLog(AbstractStaticResourceService.class);
    private static final String SLASH = "/";
    private static final String DELIMIT = ".";

    protected URI uriPrefix;
    protected URI fileHome;

    @Autowired
    private VFSHelper vfsHelper;

    @Override
    public boolean typeIsAllow(String fileName) {
        String suffix = getSuffix(fileName).substring(1);
        return ArrayUtils.contains(ALLOW_IMG, suffix.toLowerCase());
    }

    @Override
    public String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(DELIMIT));
    }

    @Override
    public void deleteResource(String path) throws IOException {
        if (path == null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(fileHome.toString());
        if (!stringBuilder.toString().endsWith(SLASH) && !path.startsWith(SLASH)) {
            stringBuilder.append(SLASH);
        }
        stringBuilder.append(path);

        vfsHelper.handle(stringBuilder.toString(), FileObject::delete);
    }

    @Override
    public URI uploadResource(String path, InputStream data) throws IOException, IllegalStateException, URISyntaxException {
        StringBuilder stringBuilder = new StringBuilder(fileHome.toString());

        if (!stringBuilder.toString().endsWith(SLASH) && !path.startsWith(SLASH)) {
            stringBuilder.append(SLASH);
        }
        stringBuilder.append(path);

        vfsHelper.handle(stringBuilder.toString(), file -> {
            if (file.exists()) {
                throw new IllegalStateException("" + file.toString() + " already existing");
            }
            OutputStream out = file.getContent().getOutputStream();
            try {
                StreamUtils.copy(data, out);
            } catch (IOException e) {
                throw new FileSystemException(e);
            } finally {
                try {
                    data.close();
                    out.close();
                } catch (IOException e) {
                    log.info("Exception on close stream." + e);
                }
            }
        });
        return getResource(path);
    }

    @Override
    public URI getResource(String path) throws URISyntaxException {
        return getURIByPath(path, false);
    }

    @Override
    public URI getResourceByLocalPath(String path) throws URISyntaxException {
        return getURIByPath(path, true);
    }

    /**
     * 获取指定资源的资源定位符
     *
     * @param path         资源路径
     * @param isHomePrefix 是否获取物理路径
     * @return
     * @throws URISyntaxException
     */
    private URI getURIByPath(String path, boolean isHomePrefix) throws URISyntaxException {
        StringBuilder stringBuilder = new StringBuilder();
        if (isHomePrefix) {
            stringBuilder.append(fileHome.toString());
        } else {
            stringBuilder.append(uriPrefix.toString());
        }
        if (!stringBuilder.toString().endsWith(SLASH) && !path.startsWith(SLASH)) {
            stringBuilder.append(SLASH);
        }
        stringBuilder.append(path);
        return new URI(stringBuilder.toString());
    }


    @Override
    public void deleteResource(URI uri) throws IOException {
        if (!uri.toString().startsWith(uriPrefix.toString())) {
            log.warn("can not resolve " + uri);
            return;
        }
        String path = uri.toString().substring(uriPrefix.toString().length());
        deleteResource(path);
    }
}

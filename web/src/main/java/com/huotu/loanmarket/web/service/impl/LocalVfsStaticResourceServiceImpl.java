package com.huotu.loanmarket.web.service.impl;

import com.huotu.loanmarket.web.service.StaticResourceService;
import com.huotu.loanmarket.web.service.VFSHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author allan
 * @date 30/10/2017
 */
public class LocalVfsStaticResourceServiceImpl implements StaticResourceService {
    private static final Log log = LogFactory.getLog(LocalVfsStaticResourceServiceImpl.class);

    private URI resourceHome;
    @Autowired
    private VFSHelper vfsHelper;

    @PostConstruct
    public void initService() throws URISyntaxException {
        resourceHome = new URI("http://res.51flashmall.com/resource/loanMarket/");
    }

    @Override
    public URI upload(String path, InputStream data) throws IOException, IllegalStateException, URISyntaxException {
        String fileUrl = resourceHome.toString() + path;

        vfsHelper.handle(fileUrl, file -> {
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

        return get(path);
    }

    @Override
    public URI get(String path) throws URISyntaxException {
        return new URI(resourceHome + path);
    }

    @Override
    public void delete(String path) throws IOException {

    }

    @Override
    public void delete(URI uri) throws IOException {

    }
}

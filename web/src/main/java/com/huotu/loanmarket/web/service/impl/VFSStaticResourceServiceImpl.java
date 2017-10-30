package com.huotu.loanmarket.web.service.impl;

import com.huotu.loanmarket.web.service.StaticResourceService;
import com.huotu.loanmarket.web.service.VFSHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs2.FileSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author allan
 * @date 27/10/2017
 */
@Service
@Profile("container")
public class VFSStaticResourceServiceImpl implements StaticResourceService {
    private static final Log log = LogFactory.getLog(VFSStaticResourceServiceImpl.class);

    private URI uriPrefix;
    private URI fileHome;

    @Autowired
    private VFSHelper vfsHelper;

    @Autowired
    public void initService(Environment environment) throws URISyntaxException {
        String uriPrefix = environment.getProperty("resourceUri", (String) null);
        if (uriPrefix == null) {
            throw new IllegalStateException("未设置resourceUri");
        }
        String fileHome = environment.getProperty("resourceHome", (String) null);
        if (fileHome == null) {
            throw new IllegalStateException("未设置resourceHome");
        }

        this.uriPrefix = new URI(uriPrefix);
        this.fileHome = new URI(fileHome);
    }

    @Override
    public URI upload(String path, InputStream data) throws IOException, IllegalStateException, URISyntaxException {
        StringBuilder filePath = new StringBuilder(fileHome.toString());
        if (!filePath.toString().endsWith("/") && !path.startsWith("/")) {
            filePath.append("/");
        }
        filePath.append(path);

        vfsHelper.handle(filePath.toString(), file -> {
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

        return null;
    }

    @Override
    public URI get(String path) throws URISyntaxException {
        StringBuilder stringBuilder = new StringBuilder(uriPrefix.toString());
        if (!stringBuilder.toString().endsWith("/") && !path.startsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(path);
        return new URI(stringBuilder.toString());
    }

    @Override
    public void delete(String path) throws IOException {

    }

    @Override
    public void delete(URI uri) throws IOException {

    }
}

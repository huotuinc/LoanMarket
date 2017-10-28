package com.huotu.loanmarket.web.service.impl;

import com.huotu.loanmarket.web.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author allan
 * @date 26/10/2017
 */
@Service
@Profile("!container")
public class LocalStaticResourceServiceImpl implements StaticResourceService {
    private URI uriPrefix;
    private URI fileHome;

    @Autowired
    public void initService(WebApplicationContext context) throws URISyntaxException {
        this.fileHome = new URI(context.getServletContext().getRealPath("/resource/upload/"));
        this.uriPrefix = new URI("http://localhost:8080" + context.getServletContext().getContextPath() + "/resource/upload/");
    }


    @Override
    public URI upload(String path, InputStream data) throws IOException, IllegalStateException, URISyntaxException {
        File file = new File(fileHome.toString(), path);
        file.getParentFile().mkdirs();

        if (file.exists()) {
            throw new IllegalStateException("" + file.toString() + " already existing");
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            StreamUtils.copy(data, outputStream);
        }
        return get(path);
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

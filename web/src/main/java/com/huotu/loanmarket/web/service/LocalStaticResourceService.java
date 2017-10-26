package com.huotu.loanmarket.web.service;

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
public class LocalStaticResourceService implements StaticResourceService {
    public static final String RESOURCE_PATH = "/_resources";
    private URI uriPrefix;
    private URI fileHome;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext context) {
        File file = new File(context.getServletContext().getRealPath("/"));
        this.fileHome = file.toURI();
        String url = System.getProperty("user.dir");
        StringBuilder stringBuilder = new StringBuilder("http://localhost:8080");
        stringBuilder.append(context.getServletContext().getContextPath());
        try {
            this.uriPrefix = new URI(stringBuilder.toString());
        } catch (URISyntaxException e) {
            throw new InternalError("解析" + stringBuilder.toString() + "失败");
        }
    }

    @Override
    public URI uploadResource(String urlPrefix, String path, InputStream data) throws IOException, IllegalStateException, URISyntaxException {
        File file = new File(fileHome.toString(), path);
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs();
        if (file.exists()) {
            throw new IllegalStateException("" + file.toString() + " already existing");
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            StreamUtils.copy(data, outputStream);
        }
        return file.toURI();
    }

    @Override
    public URI getResource(String urlPrefix, String path) throws URISyntaxException {
        StringBuilder stringBuilder = new StringBuilder(uriPrefix.toString());
        if (!stringBuilder.toString().endsWith("/") && !path.startsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(path);
        return new URI(stringBuilder.toString());
    }

    @Override
    public void deleteResource(String path) throws IOException {

    }

    @Override
    public void deleteResource(URI uri) throws IOException {

    }
}

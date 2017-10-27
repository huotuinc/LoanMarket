package com.huotu.loanmarket.web.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 静态资源服务
 *
 * @author allan
 * @date 26/10/2017
 */
public interface StaticResourceService {
    URI uploadResource(String urlPrefix, String path, InputStream data) throws IOException, IllegalStateException, URISyntaxException;

    URI getResource(String urlPrefix, String path) throws URISyntaxException;

    void deleteResource(String path) throws IOException;

    void deleteResource(URI uri) throws IOException;
}

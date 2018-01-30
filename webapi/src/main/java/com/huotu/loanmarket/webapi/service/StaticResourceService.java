package com.huotu.loanmarket.webapi.service;

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
    URI upload(String path, InputStream data) throws IOException, IllegalStateException, URISyntaxException;

    URI get(String path) throws URISyntaxException;

    void delete(String path) throws IOException;

    void delete(URI uri) throws IOException;
}

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2017. All rights reserved.
 */

package com.huotu.loanmarket.service.service.upload;


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 静态资源处理服务
 * <p>静态资源我们认为它拥有几个属性</p>
 * <ul>
 * <li>URLPrefix 资源定位符前置 比如http://www.baidu.com/</li>
 * <li>URLPath   资源路径      比如resource/taskimg/abcdefg.png</li>
 * <li>URL = URLPrefix+URLPath比如http://www.baidu.com/resource/taskimg/abcdefg.png</li>
 * <li>URLFile   资源实际位置</li>
 * </ul>
 * <p>
 * 服务端需要保证URL可以访问(读权限)URLFile
 * 同时服务端程序具有写入URLFile的权限或者拥有该权限的用户信息
 * </p>
 *
 * @author CJ
 */
public interface StaticResourceService {
    /**
     * 商品图片
     */
    String IMG = "image/{0}/";
    /**
     * 临时目录
     */
    String IMG_TEMPLATE = "image/template/{0}/";
    /**
     * 视频文件
     */
    String VEDIO = "vedio/";

    String HEAD_IMG = IMG + "head/{1}/{2}";

    /**
     * 临时目录
     */
    String HEAD_IMAGE_TEMPLATE = IMG_TEMPLATE + "head/{1}/{2}";

    String ID_CARD_IMG = IMG + "idcard/{1}/{2}";
    /**
     * 产品目录
     */
    String PROJECT__IMG = IMG + "project/{1}";
    /**
     * 临时文件目录
     */
    String ID_CARD_IMG_TEMPLATE = IMG_TEMPLATE + "idcard/{1}/{2}";

    String FAKE_DATA_IMG = IMG + "face/{1}/{2}";

    /**
     * pdf签名文件 pdf/{userid}
     */
    String ESIGN_PDF = "pdf/{0}";


    /**
     * 支持的图片格式
     */
    String[] ALLOW_IMG = new String[]{"jpg", "png", "jpeg", "bmp"};

    boolean typeIsAllow(String fileName);

    String getSuffix(String fileName);

    /**
     * 上传资源
     *
     * @param path 资源路径
     * @param data 数据
     * @return 新资源的资源定位符
     * @throws IOException           保存是出错
     * @throws IllegalStateException 如果该资源已存在
     */
    URI uploadResource(String path, InputStream data) throws IOException, IllegalStateException, URISyntaxException;

    /**
     * 获取指定资源的资源定位符
     *
     * @param path
     * @return
     * @throws URISyntaxException
     */
    URI getResource(String path) throws URISyntaxException;

    /**
     * 获取指定资源的资源定位符-物理路径
     *
     * @param path
     * @return
     * @throws URISyntaxException
     */
    URI getResourceByLocalPath(String path) throws URISyntaxException;

    /**
     * 删除资源
     *
     * @param path
     * @throws IOException
     */
    void deleteResource(String path) throws IOException;

    /**
     * 删除资源
     *
     * @param uri
     * @throws IOException
     */
    void deleteResource(URI uri) throws IOException;

}

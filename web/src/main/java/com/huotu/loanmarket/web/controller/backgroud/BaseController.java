package com.huotu.loanmarket.web.controller.backgroud;

import com.alibaba.fastjson.JSONObject;
import com.huotu.loanmarket.common.utils.StringUtilsExt;
import com.huotu.loanmarket.web.common.ApiResult;
import com.huotu.loanmarket.web.common.ResultCodeEnum;
import com.huotu.loanmarket.web.service.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * @author allan
 * @date 25/10/2017
 */
@Controller
@RequestMapping("/backend")
public class BaseController {
    @Autowired
    private StaticResourceService resourceService;

    @RequestMapping("/upload")
    public ApiResult upload(@RequestParam(value = "btnFile", required = false) MultipartFile files) throws IOException, URISyntaxException {
        Date now = new Date();
        String fileName = files.getOriginalFilename();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String path = "loanMarket/" + StringUtilsExt.dateFormat(now, "yyyyMMdd") + "/"
                + StringUtilsExt.dateFormat(now, "yyyyMMddHHmmSS") + "." + prefix;
        URI uri = resourceService.uploadResource(null, path, files.getInputStream());

        JSONObject responseData = new JSONObject();
        responseData.put("fileUrl", uri);
        responseData.put("fileUri", path);

        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, responseData);
    }
}

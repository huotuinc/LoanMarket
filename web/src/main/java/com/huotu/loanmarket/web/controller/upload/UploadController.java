package com.huotu.loanmarket.web.controller.upload;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.StringUtils;
import com.huotu.loanmarket.service.service.upload.StaticResourceService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping(value = "/resource/upload", method = RequestMethod.POST)
public class UploadController {
    @Autowired
    private StaticResourceService staticResourceService;

    @RequestMapping("/img")
    @ResponseBody
    public ApiResult upload(@RequestParam(value = "file", required = false) MultipartFile file, int imgType) throws IOException, URISyntaxException {
        String fileName = file.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) {
            throw new FileNotFoundException("未上传任何图片");
        }
        // TODO: 27/10/2017 图片属性校验
        String path;
        if (imgType == 0) {
            path = StaticResourceService.PROJECT__IMG +
                    "project-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS")) + staticResourceService.getSuffix(fileName);
        } else {
            path = StaticResourceService.PROJECT__IMG +
                    "category-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS")) + staticResourceService.getSuffix(fileName);
        }

        URI uri = staticResourceService.uploadResource(path, file.getInputStream());
        JSONObject responseData = new JSONObject();
        responseData.put("fileUrl", uri);
        responseData.put("filePath", path);
        return ApiResult.resultWith(ResultCodeEnum.SUCCESS, responseData);
    }
}

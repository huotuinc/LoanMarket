package com.huotu.loanmarket.webapi.controller.sesame;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author hxh
 * @Date 2018/1/30 11:21
 */
@Controller
@RequestMapping("/api/sesame")
public class SesameController {

    @RequestMapping("/verifyIdAndName")
    @ResponseBody
    public ApiResult verifyIdAndName(@RequestHeader(Constant.APP_MERCHANT_ID_KEY) Integer merchantId,
                                     @RequestHeader(value = Constant.APP_USER_ID_KEY, required = false) Integer userId) {

        return null;
    }
}

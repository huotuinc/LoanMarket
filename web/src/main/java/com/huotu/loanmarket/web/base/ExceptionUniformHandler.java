package com.huotu.loanmarket.web.base;

import com.huotu.loanmarket.common.utils.ApiResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常同意处理器
 *
 * @author allan
 * @date 30/10/2017
 */
@Controller
@ControllerAdvice
public class ExceptionUniformHandler {
    private static final Log log = LogFactory.getLog(ExceptionUniformHandler.class);

    /**
     * 所有异常统一处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult runtimeExceptionHandler(Exception ex) {
        log.error(ex);
        log.info("exception:" + ex.getMessage());
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST.getResultCode(), ex.getMessage(), null);
    }
}

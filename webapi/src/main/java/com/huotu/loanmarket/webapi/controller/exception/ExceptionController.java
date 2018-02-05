package com.huotu.loanmarket.webapi.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 定义一些异常的处理
 * @author helloztt
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(OrderNotFoundException.class)
    public String merchantNullException(OrderNotFoundException exception, Model model){
        model.addAttribute("code", HttpStatus.FORBIDDEN);
        model.addAttribute("msg",exception.getMessage());
        return "exception/error";
    }
}

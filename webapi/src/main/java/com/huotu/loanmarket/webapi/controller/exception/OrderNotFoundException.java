package com.huotu.loanmarket.webapi.controller.exception;

/**
 * Created by 鲁源源 on 2017/11/5.
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}

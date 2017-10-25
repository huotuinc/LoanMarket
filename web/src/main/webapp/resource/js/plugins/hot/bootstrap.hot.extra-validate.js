/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

$.validator.setDefaults({
    highlight: function (e) {
        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
    },
    errorElement: "span",
    success: function (e) {
        e.closest(".form-group").removeClass("has-error")
        e.closest(".form-group").find(".help-block").remove();
    },
    errorPlacement: function (e, r) {
        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
    },
    errorClass: "help-block"
});

/**
 * 一些验证扩展方法
 *
 */
$.validator.addMethod("mobile", function (value, element) {
    if (/^1[1-9][0-9]{9}$/.test(value)) {
        return true;
    } else {
        return false;
    }
}, "请输入合法的手机号码");

$.fn.extend({
    validateCustom: function (callback, option) {
        var op = {
            onfocusout: function (element) {
                $(element).valid();
            },
            submitHandler: function (form) { //通过回掉
                callback(form);
                return false;
            },
            invalidHandler: function (form, validator) {  //不通过回调
                return false;
            }
        }
        if (option) {
            op = $.extend({}, op, option);
        }
        $(this).validate(op);
    }
});
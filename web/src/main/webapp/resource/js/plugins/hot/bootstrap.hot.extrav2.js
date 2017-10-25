/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

//载入alertify
document.write('<link rel="stylesheet" href="//cdn.jsdelivr.net/alertifyjs/1.8.0/css/alertify.min.css"/>');
document.write('<link rel="stylesheet" href="//cdn.jsdelivr.net/alertifyjs/1.8.0/css/themes/default.min.css"/>');
document.write('<script src="//cdn.jsdelivr.net/alertifyjs/1.8.0/alertify.min.js"></script>');


//var modal = function () {
//    this.content = null;
//    this.modalId = null;
//    //初始化modal
//    this.init = function (modalId, title, content, buttons) {
//        this.content = content.html();
//        this.modalId = modalId;
//        var modal = '<div class="modal fade" id="' + modalId + '" tabindex="-1" role="dialog" aria-labelledby="' + modalId + 'Label" aria-hidden="true">';
//        modal += '<div class="modal-dialog">';
//        modal += '<div class="modal-content">';
//        modal += '<div class="modal-header">';
//        modal += '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>';
//        modal += '<h4 class="modal-title" id="myModalLabel">' + title + '</h4>';
//        modal += '</div>';
//        modal += '<div class="modal-body">';
//        modal += this.content;
//        modal += '</div>';
//        modal += '<div class="modal-footer">';

//        modal += '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>';

//        var index = 0;
//        if (typeof buttons != 'undefined') {

//            for (var buttonName in buttons) {
//                var buttonStyle = "";
//                var style = buttons[buttonName]["style"];
//                if (typeof style != 'undefined') {
//                    buttonStyle = style;
//                }
//                var buttonFunc = buttons[buttonName]["func"];
//                modal += '<button id="' + modalId + 'btn' + index + '" type="button" class="btn ' + buttonStyle + '">' + buttonName + '</button>';
//                index++;
//            }
//        }
//        modal += '</div></div></div></div>';
//        $("body").append(modal);
//        content.empty();
//        //绑定事件
//        index = 0;
//        for (var buttonName in buttons) {
//            var buttonFunc = buttons[buttonName]["func"];
//            $("#" + modalId + "btn" + index).bind("click", buttonFunc);
//            index++;
//        }
//    };
//    //显示modal
//    this.show = function () {
//        $("#" + this.modalId).modal('show');
//    }
//    //隐藏modal
//    this.hide = function () {
//        $("#" + this.modalId).modal('hide');
//    }
//    //modal中的数据初始化
//    this.initialize = function () {
//        $("#" + this.modalId + " .modal-body").html(this.content);
//    }
//}

//$.fn.extend({
//    /**
//    初始化一个包含关闭按钮的modal
//    title: modal的名称
//    bottons: 按钮及方法
//    {
//        "确定":{
//            style: '可选',
//            func: function(){
//                //按钮执行的方法
//            }
//        }
//    }
//    **/
//    initCustomModal: function (title, bottons) {
//        var modalId = $(this).attr("id") + "_modal";
//        var newModal = new modal();
//        newModal.init(modalId, title, $(this), bottons);
//        return newModal;
//    },

//    /**
//    初始化一个包含关闭和确定按钮的modal
//    title: modal的名称
//    callback: 确定按钮执行的方法

//    **/
//    initNormalModal: function (title, callback) {
//        var modalId = $(this).attr("id") + "_modal";
//        var newModal = new modal();
//        newModal.init(modalId, title, $(this), {
//            "确定": {
//                style: "btn-success",
//                func: callback
//            }
//        });
//        return newModal;
//    },
//    /**
//    初始化一个modal
//    **/
//    modal: function (title, yes, options) {
//        var $this = $(this);
//        var content = $this.html();
//        var index = new modal();
//        index.init(title, content, yes, options);
//        $this.empty();
//        return index;
//    }
//});

var hot = {
    tip: {
        success: function (content) {
            alertify.success(content);
        },
        error: function (content) {
            alertify.error(content);
        },
        msg: function (content) {
            var $msg = $('<div class="layui-layer layui-layer-dialog layui-layer-border layui-layer-msg layui-layer-hui layer-anim" style="z-index: 99891018; top: 30%; left: 773px;"><div id="" class="layui-layer-content">请输入备注内容</div><span class="layui-layer-setwin"></span></div>')

        }
    },
    /**
     * 确认
     *
     * @param content 提示内容
     * @param callback 确认回调
     */
    confirm: function (content, callback) {
        var index = new modal();

        layer.confirm(content, {
            offset: 200,
            shade: 0,
        }, function (index) {
            callback(index);
        });
    },
    /**
     * bootstrap分页扩展
     * 将根据所传参数自动渲染输出bootstrap风格的分页按钮
     *
     * @param loadObj 待输出的dom元素
     * @param pageNo 页码,从1开始
     * @param totalPages 总页数
     * @param btnCount 需要显示的按钮数量,不包括上一页下一页首页和末页,奇数
     */
    paging: function (loadObj, pageNo, totalPages, btnCount) {
        this.currentBtnPage = 0;
        this.obj = loadObj;
        this.pageNo = pageNo;
        this.totalPages = totalPages;
        this.btnCount = btnCount;

        this.init = function (callback) {
            if (this.obj) {
                this.obj.html('');
                if (this.totalRecords == 0) {
                    this.obj.html('');
                    return;
                }
                if (this.pageNo >= totalPages) {
                    this.pageNo = totalPages;
                }
                if (this.pageNo <= 1) {
                    this.pageNo = 1;
                }

                if (this.pageNo > 1) {
                    //输出首页和上一页按钮
                    this.obj.append('<li ' + (this.pageNo == 1 ? 'class="disabled"' : '') + '><a href="javascript:goTo(1,' + callback + ')" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>');
                    this.obj.append('<li ' + (this.pageNo == 1 ? 'class="disabled"' : '') + '><a href="javascript:goTo(' + (pageNo - 1) + ',' + callback + ')"><i class="fa fa-angle-left"></i></a></li>');
                }

                var btnStart = parseInt(this.btnCount / 2);//从哪个索引开始变换
                this.btnCount = Math.min(totalPages, this.btnCount);

                if (this.pageNo > this.totalPages - btnStart) {
                    this.currentBtnPage = this.totalPages - this.btnCount + 1;
                } else {
                    this.currentBtnPage = Math.max(this.pageNo - btnStart, 1);
                }

                //输出中间八个按钮
                for (var i = this.currentBtnPage; i < this.btnCount + this.currentBtnPage; i++) {
                    this.obj.append('<li ' + (this.pageNo == i ? 'class="active"' : '') + '><a href="javascript:goTo(' + i + ',' + callback + ')">' + i + '</a></li>');
                }

                //输出下一页和末页
                if (this.pageNo != totalPages) {
                    this.obj.append('<li ' + (pageNo == totalPages ? 'class="disabled"' : '') + '><a href="javascript:goTo(' + (pageNo + 1) + ',' + callback + ')"><i class="fa fa-angle-right"></i></a></li>');
                    this.obj.append('<li ' + (pageNo == totalPages ? 'class="disabled"' : '') + '><a href="javascript:goTo(' + totalPages + ',' + callback + ')" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>');
                }
            }
        };
    },
    ajax: function (url, data, success, error, type, loadingDelay) {
        var loadingIndex = layer.load();
        if (loadingDelay == "undefined") {
            loadingDelay = 0;
        }
        setTimeout(function () {
            var op = {
                type: type,
                url: url,
                data: data,
                dataType: 'json',
                success: function (data) {
                    layer.close(loadingIndex);
                    if (data == null || data == undefined) {
                        if (typeof error == 'function')
                            error();
                    } else {
                        if (typeof success == 'function')
                            success(data);
                    }
                },
                error: function () {
                    layer.close(loadingIndex);
                }
            };
            $.ajax(op);
        }, loadingDelay);
    }
}


function goTo(pageNo, callback) {
    callback(pageNo);
}

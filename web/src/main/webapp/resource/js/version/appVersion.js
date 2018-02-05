/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

/**
 * Created by guomw on 2017/12/26.
 */

/**
 * 编辑页面
 * @type {{url: string, save: editHandler.save, checkForm: editHandler.checkForm}}
 */
var editHandler = {
    url: "/version",
    save: function () {
        if (!editHandler.checkForm())
            return;
        var data = {
            vid: $("#form_id").val(),
            versionCode: $("#form_versionCode").val(),
            version: $("#form_version").val(),
            updateLink: $("#form_updateLink").val(),
            md5: $("#form_md5").val(),
            updateType: $("#form_updateType").val(),
            content: $("#form_content").val(),
            size: $("#form_size").val(),
            deviceType: $("#form_deviceType").val(),
            packageType:$("#form_packageType").val(),
        };
        hot.ajax(editHandler.url + "/save", data, function (ret) {
            if (ret.data != null) {
                $("#form_id").val(ret.data.vid);
            }
            if (ret.resultCode == 2000) {
                hot.tip.success("保存成功，即将刷新页面", function () {
                    window.location.reload();
                }, 1000);
            }
            else {
                hot.tip.error(ret.resultMsg);
            }
        }, function (err) {
            hot.tip.error(err.statusText);
        }, "post", 300);
    },
    /**
     * 检查
     * @returns {boolean}
     */
    checkForm: function () {
        if (parseInt($("#form_versionCode").val()) < 0) {
            hot.tip.error("请输入你把版本号");
            $("#form_versionCode").focus();
            return false;
        }

        if ($("#form_version").val().length == 0) {
            hot.tip.error("请输入版本号");
            $("#form_version").focus();
            return false;
        }
        if ($("#form_updateLink").val().length == 0) {
            hot.tip.error("请输入更新地址");
            $("#form_updateLink").focus();
            return false;
        }

        var deviceType = $("#form_deviceType").val();
        if (deviceType == 1) {
            if ($("#form_md5").val().length == 0) {
                hot.tip.error("请输入文件MD5");
                $("#form_md5").focus();
                return false;
            }

            if ($("#form_size").val().length == 0) {
                hot.tip.error("请输入文件大小");
                $("#form_size").focus();
                return false;
            }
        }
        if ($("#form_content").val().length == 0) {
            hot.tip.error("请输入更新内容");
            $("#form_content").focus();
            return false;
        }

        return true;
    },
    /**
     * 选
     * @param obj
     * @param enable
     */
    selectedTab: function (obj, enable) {
        // var idx = $(obj).attr("data-value");
        // var text = $(obj).text();
        // if (!enable) {
        //     $('.dropdown_text').text(text);
        //     $('#form_dropdown_value').val(idx);
        // } else {
        //     $('.dropdown_enable_text').text(text);
        //     $('#form_dropdown_enable_value').val(idx);
        // }

        var idx = $(obj).attr("data-value");
        var text = $(obj).text();
        var groupDom = $(obj).parent().parent().parent();
        groupDom.find(".dropdown_text").text(text);
        groupDom.find("input").val(idx);


        var deviceType = $("#form_deviceType").val();
        if (deviceType != 1) {
            $(".ios-devcieType").hide();
        }
        else {
            $(".ios-devcieType").show();
        }

    }
}


/**
 * 列表页面
 * @type {{requestFlag: boolean, data: {messageType: number, pageIndex: number, pageSize: number}, search: listHandler.search, doSearch: listHandler.doSearch, newTab: listHandler.newTab}}
 */
var listHandler = {
    url: "/version",
    requestFlag: false,
    templateHtml: $(".user-modal-form").html(),
    data: {
        updateType: -1,
        pageIndex: 1,
        pageSize: 10,
    },
    search: function () {
        if (this.requestFlag) return;
        this.requestFlag = true;
        hot.ajax(listHandler.url + "/listDo", this.data, function (data) {
            if (data.resultCode == 2000) {
                var html = "";
                for (var i = 0; i < data.data.list.length; i++) {
                    var row = data.data.list[i];
                    html += '<tr><td>' + ((listHandler.data.pageIndex - 1) * 10 + i + 1) + '</td>' +
                        '<td>' + row.deviceType + '</td>' +
                        '<td>' + row.versionCode + '</td>' +
                        '<td>' + row.version + '</td>' +
                        '<td>' + row.updateDesc + '</td>' +
                        '<td>' + row.md5 + '</td>' +
                        '<td>' + row.size + '</td>' +
                        '<td>' + (row.updateType == 1 ? '强制更新' : '普通更新') + '</td>' +
                        '<td>' +
                        '<span class="btn btn-myRed btn-xs"  onclick=\'listHandler.setPackageType(' + row.vid + ',' + (row.packageType == 'Normal' ? 0 : 1) + ')\'>' + (row.packageType == 'Normal' ? "开启变包" : "关闭变包") + '</span> ' +
                        '<span class="btn btn-myRed btn-xs" onclick=\'listHandler.newTab("' + row.vid + '")\'>编辑</span> ' +
                        '<span class="btn btn-myRed btn-xs" onclick=\'listHandler.delete("' + row.vid + '")\'>删除</span> ' +
                        '</td></tr>';
                }
                $("#list").html(html);
                $("#total").html("共" + data.data.totalCount + "条记录，当前第" + listHandler.data.pageIndex
                    + "/" + data.data.pageCount + "，每页" + listHandler.data.pageSize + "条记录");
                //初始化分页
                var pageinate = new hot.paging(".pagination", listHandler.data.pageIndex, data.data.pageCount, 7);
                pageinate.init(function (p) {
                    listHandler.data.pageIndex = p;
                    listHandler.search();
                });
                listHandler.requestFlag = false;

            } else {
                hot.tip.error("加载失败：" + data.resultMsg);
                listHandler.requestFlag = false;
            }
        }, function () {
        }, "post", 100);
    }
    ,
    doSearch: function () {
        //listHandler.data.messageType = $("#status").val();
        listHandler.data.pageIndex = 1;
        listHandler.search();
    },
    newTab: function (itemId) {
        hot.newTab("/version/edit/" + itemId, "编辑-" + itemId);
    },
    add: function () {
        hot.newTab("/version/edit/0", "新增");
    },
    delete: function (itemId) {
        var data = {
            vid: itemId
        }
        hot.ajax(editHandler.url + "/delete", data, function (ret) {
            if (ret.resultCode == 2000) {
                hot.tip.success("删除成功，即将刷新页面", function () {
                    window.location.reload();
                }, 1000);
            }
            else {
                hot.tip.error(ret.resultMsg);
            }
        }, function (err) {
            hot.tip.error(err.statusText);
        }, "post", 300);
    },
    setPackageType: function (itemId,type) {
        var data = {
            vid: itemId,
            packageType:type
        }
        hot.ajax(editHandler.url + "/updatePackageType", data, function (ret) {
            if (ret.resultCode == 2000) {
                window.location.reload();
            }
            else {
                hot.tip.error(ret.resultMsg);
            }
        }, function (err) {
            hot.tip.error(err.statusText);
        }, "post", 300);
    }
};

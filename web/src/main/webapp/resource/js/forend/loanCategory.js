$(function () {
    var url = "/category";
    $("#save").click(function () {

        if (!checkForm())
            return;

        var data = {
            categoryId: $("#categoryId").val(),
            categoryName: $("#categoryName").val(),
            categoryIcon: $("#categoryIcon").val(),
            categoryParentId: $("#categoryParentId").val()
        };

        hot.ajax(url + "/save", data, function (ret) {
            if (ret.resultCode == 2000) {
                $("#categoryId").val(ret.data.id);
                hot.tip.success("保存成功", function () {
                    window.location.reload();
                });

            }
        }, function (err) {
            hot.tip.error(err.statusText);
        }, "post", 300);

    });

    // 上传图片插件调用
    var uploader = WebUploader.create({
        auto: true,
        swf: 'http://resali.huobanplus.com/cdn/WebUploader/0.1.6/Uploader.swf',
        server: '/resource/upload/img',
        pick: {
            id: '#J_uploadFile',
            multiple: false,
            name: 'img'
        },
        accept: {
            title: 'Images',
            extensions: 'jpg,jpeg,png',
            mimeTypes: 'image/jpg,image/jpeg,image/png'
        }
    });

    uploader.on('fileQueued', function (file) {
        uploader.makeThumb(file, function (error, src) {
            if (error) {
                return;
            }
            $("#uploadCategoryIcon").attr("src", src);
        });
    });
    uploader.on('uploadSuccess', function (file, response) {
        $("#categoryIcon").val(response.filePath);
        hot.tip.success("上传成功");
        uploader.reset();
    });
    uploader.on('uploadError', function () {
        hot.tip.error("上传失败，重新上传");
        uploader.reset();
    });

    uploader.on('error', function (type) {
        if (type === 'Q_EXCEED_NUM_LIMIT') {
            hot.tip.error("超出数量限制");
        }
        if (type === 'Q_TYPE_DENIED') {
            hot.tip.error("文件类型不支持");
        }
    });
    /**
     * 检查
     * @returns {boolean}
     */
    function checkForm() {
        if ($("#categoryName").val().length == 0) {
            hot.tip.error("请输入分类标题");
            $("#categoryName").focus();
            return false;
        }
        console.log($("#categoryName").val().length);
        if($("#categoryName").val().length > 20) {
            hot.tip.error("超过限制长度");
            $("#categoryName").focus();
            return false;
        }
        if ($("#categoryIcon").val().length == 0) {
            hot.tip.error("请上传分类图标");
            return false;
        }
        return true;
    }

});
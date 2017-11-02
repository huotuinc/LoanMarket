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


    /**
     * 上传icon
     */
    $("#uploadCategoryIcon").click(function () {
        $("#fileIcon").unbind("change").bind("change", fileChangeEvent);
        $("#fileIcon").click();
    });

    function fileChangeEvent() {
        if ($("#fileIcon").val().length > 0) {
            hot.fileUpload('/resource/upload/img', 'fileIcon', null, function (res) {
                $("#categoryIcon").val(res.filePath);
                $("#uploadCategoryIcon").attr("src", res.fileUrl);
                $("#fileIcon").val("");
            });
        }
    }

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
        if ($("#categoryIcon").val().length == 0) {
            hot.tip.error("请上传分类图标");
            return false;
        }
        return true;
    }

});
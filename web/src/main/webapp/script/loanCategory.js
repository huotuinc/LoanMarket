$(function () {
    var url = "/backend/category"
    $("#save").click(function () {

        var data = {
            categoryId: $("#categoryId").val(),
            categoryName: $("#categoryName").val(),
            categoryIcon: $("#categoryIcon").val(),
            categoryParentId: $("#categoryParentId").val()
        };
        J.PostJson(url + "/save", data, function (ret) {
            if (ret.resultCode == 2000) {
                alert(ret.resultMsg);
                $("#categoryId").val(ret.data.Id);
            }
        }, function (err) {

        });

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
                $("#categoryIcon").val(res.fileUrl);
                $("#uploadCategoryIcon").attr("src", res.fileUrl);
                $("#fileIcon").val("");

            });
        }
    }


});
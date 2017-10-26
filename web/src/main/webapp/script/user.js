$(function () {
    $('#J_inputFile').change(function () {
        var self = $(this);
        var data = new FormData();
        data.append('avatar', $('#J_inputFile')[0].files[0]);
        $.showLoading('上传中');
        $.ajax('/update/avatar', {
            method: 'POST',
            data: data,
            contentType: false,
            processData: false,
            dataType: 'json',
            success: function (res) {
                $.hideLoading();
                if (res.resultCode !== 200) {
                    return $.toptip(res.resultMsg, 'error');
                }
                self.closest('.js-loginBox').find('img').attr('src', res.data.avatar);
            },
            error:function () {
                $.hideLoading();
                $.toptip('请求失败', 'error');
            }
        });
    });
});
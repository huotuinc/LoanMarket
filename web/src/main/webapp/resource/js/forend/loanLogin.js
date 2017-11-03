$(function () {
    FastClick.attach(document.body);

    $.toast.prototype.defaults.duration = 1000;
    var loginModal = '<div class="login-modal">' +
        '        <div class="input-wrap">' +
        '            <div class="input">' +
        '                <input class="input" type="tel" id="J_loginMobile" name="mobile" title="手机号" placeholder="请输入手机号"></div>' +
        '            </div>' +
        '        <div class="input-wrap input-group">' +
        '            <div class="input">' +
        '                <input class="input" type="text" id="J_authCode" name="authCode" title="验证码"' +
        '                       placeholder="请输入验证码">' +
        '            </div>' +
        '            <button type="button" class="btn js-authCodeBtn">获取验证码</button>' +
        '        </div>' +
        '    </div>';
    var loginUrl = '/forend/verifyCodeCheck';
    var sendAuthCodeUrl = '/forend/verifyCode';
    var applyCount = '/rest/api/project/applyLog';
    $('.js-needLogin').click(function () {
        $.ajax("/forend/checkLogin", {
                method: "GET",
                async: false,
                dataType: 'json',
                success: function (res) {
                    if (res.resultCode == 2000) {
                        $.ajax(applyCount, {
                                method: 'POST',
                                data: {
                                    userId: res.data,
                                    projectId: $("#projectId").html()
                                },
                                dataType: 'json',
                                success: function (res) {
                                    if (res.resultCode == 2000) {
                                        return $.toast('申请成功');
                                    }
                                    return $.toast('申请失败', 'cancel');
                                },
                                error: function () {
                                    $.toptip("系统错误");
                                }
                            }
                        );
                    }
                },
                error: function () {
                    $.toptip("系统错误");
                }
            }
        );
        var self = $(this);
        var flag = self.hasClass('js-loginBox');
        $.modal({
            title: "快速登录",
            text: loginModal,
            autoClose: false,
            buttons: [
                {
                    text: "取消",
                    className: "default",
                    onClick: function () {
                        $.closeModal();
                    }
                },
                {
                    text: "登录",
                    onClick: function () {
                        if (verifyPhone()) {
                            var mobile = $('#J_loginMobile').val();
                            var authCode = $('#J_authCode').val();
                            if (authCode) {
                                $.showLoading('登录中');
                                $.ajax(loginUrl, {
                                    method: 'POST',
                                    data: {
                                        mobile: mobile,
                                        verifyCode: authCode
                                    },
                                    dataType: 'json',
                                    success: function (res) {
                                        $.hideLoading();
                                        if (res.resultCode !== 2000) {
                                            return $.toast(res.resultMsg, 'cancel');
                                        }
                                        self.removeClass('js-needLogin').off('click');
                                        if (flag) {
                                            showLogin(self, res.data);
                                        }
                                        $.toast('登录成功');
                                        $.closeModal();
                                    }
                                });
                            } else {
                                $.hideLoading();
                                $.toptip('请求失败', 'error');
                            }
                        }
                    }
                }
            ]
        });

        return false;
    });

    function verifyPhone() {
        var mobile = $('#J_loginMobile').val();
        if (!/^1([34578])\d{9}$/.test(mobile)) {
            $.toast("请输入正确的手机号", "cancel");
            return false;
        }
        return true;
    }

    $(document).on('click', '.js-authCodeBtn', function () {
        var mobile = $('#J_loginMobile').val();
        if (verifyPhone()) {
            sendSMS($(this));
            $.ajax(sendAuthCodeUrl, {
                method: 'POST',
                data: {
                    mobile: mobile
                },
                dataType: 'json',
                success: function (res) {
                    if (res.resultCode !== 2000) {
                        $.toptip(res.resultMsg);
                        return false;
                    }
                    $.toast('发送成功');
                },
                error: function () {
                    $.toptip("系统错误");
                }
            });
        }
    });

    function sendSMS(ele) {
        ele.prop('disabled', true);
        var s = 60;
        var t = setInterval(function () {
            ele.text(s-- + 's');
            if (s === -1) {
                clearInterval(t);
                ele.text('获取验证码')
                    .prop('disabled', false);
            }
        }, 1000);
    }

    function showLogin(ele, res) {
        ele.find('img').attr('src', res.avatar)
            .end()
            .find('p').text(res.name)
            .end()
            .addClass('js-uploadAvatar');
    }
});
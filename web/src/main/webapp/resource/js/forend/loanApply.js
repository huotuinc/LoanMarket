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
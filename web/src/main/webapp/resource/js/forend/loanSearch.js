$(function () {
    FastClick.attach(document.body);
    var $amount = $('#J_amount');
    var $deadline = $('#J_deadline');
    var tag = getQueryString('tag');
    var amount = parseInt($amount.text());
    var deadline = parseInt($deadline.text());
    $amount.picker({
        cols: [
            {
                textAlign: 'center',
                values: ['500元', '1000元', '2000元', '3000元', '4000元', '5000元', '6000元', '7000元', '8000元', '9000元', '10000元']
            }
        ],
        onChange: function (p, v) {
            $amount.text(v);
        },
        onClose: function (p) {
            if( amount !== parseInt(p.value[0])) {
                amount = parseInt(p.value[0]);
                searchLoan(tag, amount, deadline);
            }
        }
    });

    $deadline.picker({
        cols: [
            {
                textAlign: 'center',
                values: ['1个月', '2个月', '3个月', '4个月', '5个月', '6个月', '7个月', '8个月', '9个月', '10个月', '11个月', '12个月']
            }
        ],
        onChange: function (p, v) {
            $deadline.text(v);
        },
        onClose: function (p) {
            if( deadline !== parseInt(p.value[0])) {
                deadline = parseInt(p.value[0]);
                searchLoan(tag, amount, deadline);
            }
        }

    });


    function searchLoan(tag, amount, deadline) {
        $.showLoading();
        $.ajax('/forend/project/search/list', {
            method: 'GET',
            data: {
                categoryId: tag,
                amount: amount,
                deadline: deadline
            },
            dataType: 'json',
            success: function (res) {
                $.hideLoading();
                if(res.resultCode !== 2000) {
                    return $.toptip('请求失败', 'error');
                }
                var _html = template('J_resultTmp', res);
                $('#J_resultContainer').html(_html);
            },
            error: function () {
                $.hideLoading();
                $.toptip('请求失败', 'error');
            }
        });
    }


    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r !== null) return decodeURIComponent(r[2]);
        return null;
    }

    searchLoan(tag, amount, deadline);
});
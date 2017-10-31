$(function () {
    var $line = $('#J_line');
    var $deadline = $('#J_deadline');
    var line = $line.data('line');
    var deadline = $deadline.data('deadline');
    var lineArray = line.split(',');
    var deadlineArray = deadline.split(',');
    var unit = $deadline.data('unit') === '日' ? '日': '个月';
    var lineCalc = lineArray[0];
    var deadlineCale = deadlineArray[0];

    $line.find('.weui-cell__ft').picker({
        cols: [
            {
                textAlign: 'center',
                values: lineArray
            }
        ],
        onChange: function (p, v) {
            $("#J_line").find('.weui-cell__ft').text(v + '元');
        },
        onClose: function (p) {
            lineCalc = p.value[0];
            calc(lineCalc, deadlineCale);
        }
    });

    $deadline.find('.weui-cell__ft').picker({
        cols: [
            {
                textAlign: 'center',
                values: deadlineArray
            }
        ],
        onChange: function (p, v) {
            $("#J_deadline").find('.weui-cell__ft').text(v + unit);
        },
        onClose: function (p) {
            deadlineCale = p.value[0];
            calc(lineCalc, deadlineCale);
        }
    });

    function calc(lineSet, deadlineSet) {
        var interestRate = toPoint($('#J_interestRate').text());
        var dayInterest = (10000 * interestRate) / (10000 / lineSet);
        var res = (lineSet / deadlineSet) + dayInterest;
        $('#J_repayment').text(res.toFixed(2) + '元');

    }


    function toPoint(percent) {
        var str = percent.replace('%', '');
        str = str / 100;
        return str;
    }

    calc(lineCalc, deadlineCale);
});
$(function () {
    var $line = $('#J_line');
    var $deadline = $('#J_deadline');
    // 贷款金额数据，取值为 #J_line 的 data-line 属性，服务端请留意渲染。忽略当初命名。讲错就错吧~
    var line = $line.data('line');
    // 分期期限，取值为 #J_deadline 的 data-deadline 属性，服务端请留意渲染
    var deadline = $deadline.data('deadline');
    // 数据处理成数组，处理可能仅仅有一个值的情况
    var lineArray = [];
    var deadlineArray = [];
    try {
        lineArray = line.split(',');
    } catch (e) {
        lineArray.push(line)
    }
    try {
        deadlineArray = deadline.split(',');
    } catch (e) {
        deadlineArray.push(deadline);
    }
    // 获取分期期限单位，取值为 #J_deadline 的 data-unit 属性，服务端请留意渲染
    var unit = $deadline.data('unit') === '日' ? '日' : '个月';
    // 全局变量。获取贷款金额数组和分期期限数组的第一个参数，用于页面的初始化渲染 见64行
    var lineCalc = lineArray[0];
    var deadlineCale = deadlineArray[0];

    // 初始化picker 插件 贷款金额
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
            // 修改lineCalc的值
            lineCalc = p.value[0];
            // 关闭picker 重新计算数据
            calc(lineCalc, deadlineCale);
        }
    });
    // 初始化picker 插件
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
            // 修改deadlineCale的值
            deadlineCale = p.value[0];
            // 关闭picker 重新计算数据
            calc(lineCalc, deadlineCale);
        }
    });

    /**
     * 计算 每日/月应还
     * @param lineSet 贷款金额
     * @param deadlineSet 分期期限
     */
    function calc(lineSet, deadlineSet) {
        // 利率
        var interestRate = toPoint($('#J_interestRate').text());
        /**
         * 每日/月应还 计算公式是 先求出 1W每日应还，乘以 贷款金额，再除以1W。这样多此一举的原因是避免JS的浮点数问题
         * @type {number}
         */
        var dayInterest = (10000 * interestRate * lineSet) / 10000;
        var res = (lineSet / deadlineSet) + dayInterest;
        //保留两位小数，展示到页面
        $('#J_repayment').text(res.toFixed(2) + '元');

    }

    /**
     * 百分数转小数
     * @param percent 获取百分数 取值#J_interestRate的text，服务端请留意渲染
     * @returns {number}
     */
    function toPoint(percent) {
        var str = percent.replace('%', '');
        str = str / 100;
        return str;
    }

    calc(lineCalc, deadlineCale);
});
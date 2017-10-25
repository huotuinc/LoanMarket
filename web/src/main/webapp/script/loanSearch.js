$(function () {
    FastClick.attach(document.body);

    $('#J_amount').picker({
        cols: [
            {
                textAlign: 'center',
                values: ['500元', '1000元', '2000元', '3000元', '4000元', '5000元', '6000元', '7000元', '8000元', '9000元', '10000元']
            }
        ],
        onChange: function (p, v) {
            $("#J_amount").text(v);
        },
        onClose: function (p) {
            // lineCalc = p.value[0];
            // calc(lineCalc, deadlineCale);
        }
    });

    $('#J_deadline').picker({
        cols: [
            {
                textAlign: 'center',
                values: ['1个月', '2个月', '3个月', '4个月', '5个月', '6个月', '7个月', '8个月', '9个月', '10个月', '11个月', '12个月']
            }
        ],
        onChange: function (p, v) {
            $("#J_deadline").text(v);
        },
        onClose: function (p) {
            // lineCalc = p.value[0];
            // calc(lineCalc, deadlineCale);
        }
    });
});
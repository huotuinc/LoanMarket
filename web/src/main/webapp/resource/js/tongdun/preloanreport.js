$(function () {
    /*var len  = $('.risk-items-flag').length;
     $('#risk-count').text(len+'条');*/

    $("#report-a-close").on("click", function () {
        if (window.parent.frames['iframe']) {
            $('#iframe-wrap', window.parent.document).hide(0);
        } else if (window == window.top) {
            window.close();
        }
    });

    //点击其他位置关闭报告
    $(".msk").on('click', function () {
        $("#report-a-close").trigger("click");
    });

    $("#risk-count").on("click", function () {
        var scrollHeight = $("#risk-items").offset().top;
        $(".report-container").animate({scrollTop: scrollHeight}, 300);
    });

    //点击查看详情
    var id = '';
    $(".check-table").on("click", ".table-href", function () {
        $(".a-detail").hide();
        $(this).parents('ul').find('.a-detail').show(100);
        $(".msk-detail").show().css("right",(480-($(window).width()-900)/2)+'px');
    });

    $(".report-container").on("click", function (e) {
        e = e || window.event;
        e.stopPropagation();
        e.preventDefault();

        $("#report-a-close").trigger("click");
    });

    $(".container").on("click", function (e) {
        e = e || window.event;
        e.stopPropagation();
        e.preventDefault();
    })

    //详情关闭
    $(".detail-close-x").on("click", function (e) {
        $(this).parent().parent().hide(100);
        $(".msk-detail").hide();
    });
    //点击任意位置关闭详情
    $(".msk-detail").on('click', function () {
        $(".detail-close-x").trigger("click");
        $(".msk-detail").hide();
    });
    //id点击
    var display = 0;
    $("a.jz").click(function () {
        if (display == 0) {
            $(".j-rpi-toggle-target").show(400);
            display = 1;
        } else if (display == 1) {
            $(".j-rpi-toggle-target").hide(400);
            display = 0;
        }
    });

    if ($('#canvas-main').length > 0) {
        var canvasMain = $('#canvas-main')[0];
        var mainCtx = canvasMain.getContext('2d');
        var score = parseInt($('#result-score').text(), 10);
        var resultCat = $('#result-cat').text();
        var i = 1;
        if (score > 0 && score < 100) {
            i = (100 - score) / 100;
        }
        if (score === 0) {
            i = 1;
        }
        // console.log(i);
        mainCtx.rotate(-Math.PI / 2);
        mainCtx.lineWidth = 8;
        // console.log(resultCat);
        if (resultCat.indexOf('拒绝') > -1) {
            $('#result-cat').addClass('reject');
            mainCtx.strokeStyle = '#ff6c5c';
        } else if (resultCat.indexOf('通过') > -1) {
            $('#result-cat').addClass('accept');
            mainCtx.strokeStyle = '#8cdb65';
        } else if (resultCat.indexOf('审核') > -1) {
            $('#result-cat').addClass('review');
            mainCtx.strokeStyle = '#f8d436';
        }
        mainCtx.beginPath();
        mainCtx.arc(-75, 75, 69, 0, Math.PI * (i * 2), true);
        mainCtx.stroke();
        // 75, 75, 0
        // 17, 103, 0.1
        // -17, 104

        var canvasBg = $('#canvas-bg')[0];
        var bgCtx = canvasBg.getContext('2d');
        bgCtx.fillStyle = '#fafafa';
        bgCtx.beginPath();
        bgCtx.arc(61, 61, 52, 0, Math.PI * 2, true);
        bgCtx.closePath();
        bgCtx.fill();
    }

    // 多头详情点击下拉查看
    $('.dimension-title').on('click', function () {
        var dimensionList = $(this).next('.dimension-list');
        if (dimensionList.length) {
            dimensionList.slideToggle(200);
        }
    });

    $('.risk-detail-title').on('click', function () {
        var detailList = $(this).next('.risk-detail-list');
        if (detailList.length) {
            detailList.slideToggle(200);
        }
        //数据库中会出现这个※,换成*,主要原因太丑了
        var li = $(this).next('.risk-detail-list').find('.risk-detail-sub-list').children('li')
        $.each(li, function (index, value) {
            var text = $(value).text().replace(/[※]/g, '*');
            $(value).text(text);
        });
    });

    //提示报告中是否存在命中未分组的规则情况
    if ($('#tip-value').val() == 'true') {
        $('.template-tip').css({'display':'block'});
    }

});
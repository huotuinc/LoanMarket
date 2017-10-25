//一些初始化操作
$(function () {
    $(document).on("keydown", ".only-num", function (e) {
        if (e.ctrlKey) return !0;
        var t = t || window.event,
            n = t.charCode || t.keyCode;
        n != 8 && n != 9 && n != 46 && (n < 37 || n > 40) && (n < 48 || n > 57) && (n < 96 || n > 105) && (t.preventDefault ? t.preventDefault() : t.returnValue = !1, $(this).show("highlight", 150));
    });

    $(document).on("keydown", ".only-float", function (e) {
        function r(e, t) {
            t.preventDefault ? t.preventDefault() : t.returnValue = !1,
                $(e).show("highlight", 150)
        }

        if (e.ctrlKey) return !0;
        var t = t || window.event,
            n = t.charCode || t.keyCode;
        n == 110 || n == 190 ? ($(this).val().indexOf(".") >= 0 || !$(this).val().length) && r(this, t) : n != 8 && n != 9 && n != 46 && (n < 37 || n > 40) && (n < 48 || n > 57) && (n < 96 || n > 105) && r(this, t)
    });
});
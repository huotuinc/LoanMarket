/**
 * Created by admin on 2017-12-26.
 */
function changeParam(data){
    const dataParams = new URLSearchParams();
    for (var key in data) {
        dataParams.append(key, data[key]);
    }
    return dataParams;
}
function gotoHtml(html){
    window.location.href = html + window.location.search;
}

function getQuery(name) {
    var strHref = window.document.location.href;
    var intPos = strHref.indexOf("?");
    var strRight = strHref.substr(intPos + 1);
    var arrTmp = strRight.split("&");
    for (var i = 0; i < arrTmp.length; i++) {
        var arrTemp = arrTmp[i].split("=");
        if (arrTemp[0].toUpperCase() == name.toUpperCase()) return arrTemp[1];
    }
    if (arguments.length == 1)
        return "";
    if (arguments.length == 2)
        return arguments[1];
}

function getQueryString (name, defaultVal) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r !== null) return decodeURIComponent(r[2]);
    if (typeof(defaultVal) != 'undefined') {
        return defaultVal;
    }
    return null;
}
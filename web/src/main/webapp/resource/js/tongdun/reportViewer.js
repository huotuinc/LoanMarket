/**
 * @type {{showById: reportViewer.showById, showByOrderId: reportViewer.showByOrderId, _load: reportViewer._load}}
 */
var reportViewer = {
    /**
     * 根据主键id查看
     * @param id 主键id
     */
    showById: function (id) {
        var link = '/admin/tongdun/report/' + id;
        this._load(link);
    },
    /**
     * 根据订单号查看
     * @param orderId 订单号
     */
    showByOrderId: function (orderId) {
        var link = '/admin/tongdun/report/order-' + orderId;
        this._load(link);
    },
    _load: function (link) {
        var html = '<div id="iframe-wrap" class="iframe-wrap" style="display: block;">' +
            '<iframe name="iframe" class="iframe" src="' + link + '" frameborder="0" scrolling="auto"></iframe>' +
            '</div>';
        $('#iframe-wrap').remove();
        $('body').append(html);
    }
};
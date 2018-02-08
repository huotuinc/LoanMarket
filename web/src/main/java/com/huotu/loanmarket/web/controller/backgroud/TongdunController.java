package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.ApiResultException;
import com.huotu.loanmarket.service.entity.tongdun.TongdunRequestLog;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.TongdunEnum;
import com.huotu.loanmarket.service.model.tongdun.TongdunLogSearchCondition;
import com.huotu.loanmarket.service.model.tongdun.report.ReportDetailVo;
import com.huotu.loanmarket.service.service.tongdun.TongdunRequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 同盾后台请求
 * @author wm
 */
@Controller
@RequestMapping("/admin/tongdun")
public class TongdunController {

    @Autowired
    private TongdunRequestLogService requestLogService;

    /**
     * 调用日志列表
     * @param model
     * @param searchCondition
     * @return
     */
    @RequestMapping(value = "/logList", method = RequestMethod.GET)
    public String logList(Model model, @ModelAttribute(value = "searchCondition") TongdunLogSearchCondition searchCondition) {
        Page<TongdunRequestLog> requestLogPage = requestLogService.findAll(searchCondition);
        model.addAttribute("logPage", requestLogPage);
        model.addAttribute("decisionTypeList", EnumHelper.getEnumList(TongdunEnum.DecisionType.class));
        model.addAttribute("logSearchTypeList", EnumHelper.getEnumList(TongdunEnum.LogSearchType.class));
        return "tongdun/log_list";
    }

    /**
     * 报告详情，根据主键
     *
     * @param id 主键id
     * @return
     */
    @RequestMapping(value = "/report/{id}", method = RequestMethod.GET)
    public String report(@PathVariable(value = "id") Long id, Model model) {
        ReportDetailVo reportDetailVo = requestLogService.getReportDetail(id);
        if (reportDetailVo == null) {
            throw new ApiResultException(ApiResult.resultWith(AppCode.ERROR,"报告不存在"));
        }
        model.addAttribute("report", reportDetailVo);
        return "tongdun/report";
    }

    /**
     * 报告详情，根据订单号
     *
     * @param orderId 订单号
     * @return
     */
    @RequestMapping(value = "/report/order-{orderid}", method = RequestMethod.GET)
    public String reportByOrder(@PathVariable(value = "orderid") String orderId, Model model) {
        TongdunRequestLog log = requestLogService.findByOrderId(orderId);
        if (log == null) {
            throw new ApiResultException(ApiResult.resultWith(AppCode.ERROR,"报告不存在"));
        }
        if(log.getState() == TongdunEnum.ReportStatus.SUCCESS.getCode()) {
            ReportDetailVo reportDetailVo = requestLogService.convertRequestLogToReport(log);
            model.addAttribute("report", reportDetailVo);
        }else{
            throw new ApiResultException(ApiResult.resultWith(AppCode.ERROR,"报告非成功状态"));
        }
        return "tongdun/report";
    }
}
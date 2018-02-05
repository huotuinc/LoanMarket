package com.huotu.loanmarket.webapi.controller.carrier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.carrier.AsyncTask;
import com.huotu.loanmarket.service.entity.carrier.ConsumeBill;
import com.huotu.loanmarket.service.entity.carrier.RiskContactDetail;
import com.huotu.loanmarket.service.entity.carrier.RiskContactStats;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import com.huotu.loanmarket.service.model.carrier.UserCarrierVo;
import com.huotu.loanmarket.service.repository.carrier.AsyncTaskRepository;
import com.huotu.loanmarket.service.repository.carrier.ConsumeBillRepository;
import com.huotu.loanmarket.service.repository.carrier.RiskContactDetailRepository;
import com.huotu.loanmarket.service.repository.carrier.RiskContactStatsRepository;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.carrier.UserCarrierService;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.webapi.controller.exception.OrderNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 运营商相关
 * @author luyuanyuan on 2017/11/22.
 */
@RequestMapping("/api/carrier")
@Controller
public class CarrierController {
    private static final Log log = LogFactory.getLog(CarrierController.class);

    @Autowired
    private UserCarrierService userCarrierService;
    @Autowired
    private AsyncTaskRepository asyncTaskRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RiskContactStatsRepository riskContactStatsRepository;
    @Autowired
    private RiskContactDetailRepository riskContactDetailRepository;
    @Autowired
    private ConsumeBillRepository consumeBillRepository;

    @RequestMapping("/magicCallback")
    @ResponseBody
    public Object magicCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //回调事件
        String notifyEvent = request.getParameter("notify_event");
        //回调参数
        String passbackParams = request.getParameter("passback_params");
        String[] split = passbackParams.split(",");
        String orderId = split[0];
        String merchantId = split[1];
        String type = split[2];
        String notifyData = request.getParameter("notify_data");
        log.info(MessageFormat.format("【数据魔盒】回调开始，订单ID：{0}，回调参数：{1}",orderId,notifyData));
        Map<String,Object> map = new HashMap<>(2);
        map.put("code",0);
        Order order = orderRepository.findOne(orderId);
        if (order == null) {
            log.info(MessageFormat.format("订单不存在，订单id：{0}", orderId));
            map.put("message","订单不存在，回调忽略");
            return map;
        }
        if (UserAuthorizedStatusEnums.AUTH_SUCCESS.equals(order.getAuthStatus())) {
            log.info(MessageFormat.format("【数据魔盒】重复调用，订单id：{0}",orderId));
            map.put("message","重复调用");
            return map;
        }
        JsonObject jsonObject = new JsonParser().parse(notifyData).getAsJsonObject();
        response.setStatus(HttpStatus.OK.value());
        if("SUCCESS".equals(notifyEvent)){
            String taskId = jsonObject.get("task_id").getAsString();
            log.info("task_id:" + taskId);
            //回调成功,保存用户id和用户任务
            AsyncTask asyncTask = asyncTaskRepository.findByOrderIdAndType(orderId,type);
            if(asyncTask != null){
                asyncTask.setTaskId(taskId);
            }else{
                 asyncTask = new AsyncTask();
                 asyncTask.setMerchantId(Integer.valueOf(merchantId));
                 asyncTask.setOrderId(orderId);
                 asyncTask.setTaskId(taskId);
                 asyncTask.setType(type);
            }
            asyncTaskRepository.saveAndFlush(asyncTask);
        }else{
            log.error(MessageFormat.format("【数据魔盒】回调失败，回调通知事件:{0}",notifyEvent));
        }
        map.put("message","success");
        return map;
    }

    /**
     * 查询结果
     * @param taskId
     * @param merchantId
     * @param orderId
     * @return
     */
    @RequestMapping("/queryResult")
    @ResponseBody
    public ApiResult queryResult(String taskId, @RequestHeader("merchantId") Long merchantId, String orderId){
        ApiResult apiResult;
        try {
            apiResult = userCarrierService.queryResult(taskId, orderId,merchantId.intValue());
        } catch (Exception e) {
            log.error("未知错误",e);
            return ApiResult.resultWith(AppCode.ERROR.getCode(),"未知错误");
        }
        return apiResult;
    }

    /**
     * 运营商展示数据
     * @param userId
     * @param orderId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @RequestMapping("/carrierShow")
    public String carrierShow( Long userId,String orderId,Model model) {
        Order order = orderService.findByOrderId(orderId);
        if(order == null || !order.getUser().getUserId().equals(userId)) {
            throw new OrderNotFoundException(Constant.ORDER_NOT_FOUND);
        }
        UserCarrierVo userCarrierVo = userCarrierService.carrierShow(orderId);
        model.addAttribute("carrier",userCarrierVo);
        model.addAttribute("orderId",orderId);
        model.addAttribute("userId",userId);
        return "report/carrierInfo";
    }

    /**
     * 风险联系人
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping("/riskContactList")
    public String riskContactList(Long userId,String orderId,Model model) {
        Order order = orderService.findByOrderId(orderId);
        if(order == null || !order.getUser().getUserId().equals(userId)) {
            throw new OrderNotFoundException(Constant.ORDER_NOT_FOUND);
        }
        List<RiskContactStats> contactStatsList = riskContactStatsRepository.findByOrderId(orderId);
        model.addAttribute("riskContactList",contactStatsList);
        return "report/riskContact";
    }

    /**
     * 风险联系人明细
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping("/riskContactDetailList")
    public String riskContactDetailList( Long userId, String orderId,Model model) {
        Order order = orderService.findByOrderId(orderId);
        if(order == null || !order.getUser().getUserId().equals(userId)) {
            throw new OrderNotFoundException(Constant.ORDER_NOT_FOUND);
        }
        List<RiskContactDetail> riskContactDetailList = riskContactDetailRepository.findByOrderId(orderId);
        model.addAttribute("riskContactDetailList",riskContactDetailList);
        return "report/riskContactDetail";
    }

    /**
     * 话费明细
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping("/consumeBillList")
    public String consumeBillList( Long userId,String orderId, Model model) {
        Order order = orderService.findByOrderId(orderId);
        if(order == null || !order.getUser().getUserId().equals(userId)) {
            throw new OrderNotFoundException(Constant.ORDER_NOT_FOUND);
        }
        List<ConsumeBill> consumeBillList = consumeBillRepository.findByOrderId(orderId);
        consumeBillList.sort(Comparator.comparing(ConsumeBill::getMonth));
        model.addAttribute("consumeBillList",consumeBillList);
        return "report/consumeBill";
    }

}

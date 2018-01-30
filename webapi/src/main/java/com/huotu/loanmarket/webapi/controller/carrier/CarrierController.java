package com.huotu.loanmarket.webapi.controller.carrier;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.carrier.AsyncTask;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.repository.carrier.AsyncTaskRepository;
import com.huotu.loanmarket.service.repository.user.UserRepository;
import com.huotu.loanmarket.service.service.carrier.UserCarrierService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

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
    private UserRepository userRepository;

    @RequestMapping("/magicCallback")
    @ResponseBody
    public Object magicCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //回调事件
        String notifyEvent = request.getParameter("notify_event");
        //回调参数
        String passbackParams = request.getParameter("passback_params");
        String[] split = passbackParams.split(",");
        String userId = split[0];
        String merchantId = split[1];
        String notifyData = request.getParameter("notify_data");
        log.info(MessageFormat.format("【数据魔盒】回调开始，用户ID：{0}，回调参数：{1}",userId,notifyData));
        Map<String,Object> map = new HashMap<>(2);
        map.put("code",0);
        User user = userRepository.findOne(Long.valueOf(userId));
        if (user == null) {
            log.info(MessageFormat.format("用户不存在，用户id：{0}", userId));
            map.put("message","用户不存在，回调忽略");
            return map;
        }
//        if (UserAuthorizedStatusEnums.AUTH_SUCCESS.equals(user.getFlgCarrier())) {
//            log.info(MessageFormat.format("【数据魔盒】重复调用，用户id：{0}",userId));
//            map.put("message","重复调用");
//            return map;
//        }
        JsonObject jsonObject = new JsonParser().parse(notifyData).getAsJsonObject();
        response.setStatus(HttpStatus.OK.value());
        if("SUCCESS".equals(notifyEvent)){
            String taskId = jsonObject.get("task_id").getAsString();
            log.info("task_id:" + taskId);
            //回调成功,保存用户id和用户任务
            AsyncTask asyncTask = asyncTaskRepository.findOne(Long.valueOf(userId));
            if(asyncTask != null){
                asyncTask.setTaskId(taskId);
            }else{
                 asyncTask = new AsyncTask();
                 asyncTask.setMerchantId( Long.valueOf(merchantId));
                 asyncTask.setUserId(Long.valueOf(userId));
                 asyncTask.setTaskId(taskId);
            }
            asyncTaskRepository.saveAndFlush(asyncTask);
        }else{
            log.error(MessageFormat.format("运营商数据查询回调事件错误，回调通知事件:{0}",notifyEvent));
        }
        map.put("message","success");
        return map;
    }

    /**
     * 查询结果
     * @param taskId
     * @param merchantId
     * @param userId
     * @return
     */
    @RequestMapping("/queryResult")
    @ResponseBody
    public ApiResult queryResult(String taskId, @RequestHeader("merchantId") Long merchantId, @RequestHeader("userId") Long userId){
        ApiResult apiResult;
        try {
            apiResult = userCarrierService.queryResult(taskId, userId,merchantId);
        } catch (Exception e) {
            log.error("未知错误",e);
            return ApiResult.resultWith(AppCode.ERROR.getCode(),"未知错误");
        }
        return apiResult;
    }

}

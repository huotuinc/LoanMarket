package com.huotu.loanmarket.webschedule.service;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.config.LoanMarkConfigProvider;
import com.huotu.loanmarket.service.entity.carrier.AsyncTask;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.repository.carrier.AsyncTaskRepository;
import com.huotu.loanmarket.service.repository.carrier.UserCarrierRepository;
import com.huotu.loanmarket.service.service.carrier.UserCarrierService;
import com.huotu.loanmarket.service.service.ds.DsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

/**
 * 认证相关Schedule
 *
 * @author hxh
 * @date 2017-12-21
 */
@Service
public class AuthenticationSchedule {
    private static final Log log = LogFactory.getLog(AuthenticationSchedule.class);

    @Autowired
    private AsyncTaskRepository asyncTaskRepository;
    @Autowired
    private UserCarrierService userCarrierService;
//    @Autowired
//    private EmailService emailService;
    @Autowired
    private LoanMarkConfigProvider loanMarkConfigProvider;

    @Autowired
    private UserCarrierRepository userCarrierRepository;
    @Autowired
    private DsService dsService;

    /**
     * 处理数据魔盒回调任务
     * 20秒执行一次
     */
    @Scheduled(fixedDelay = 20000)
    public void handMagicTask() {
        log.info("【定时任务】处理数据魔盒回调任务");
        List<AsyncTask> asyncTaskList = asyncTaskRepository.findByFailureFalse();
        for (AsyncTask task : asyncTaskList) {
            startHand(task);
        }
    }

    public void startHand(AsyncTask task) {
        String taskId = task.getTaskId();
        String orderId = task.getOrderId();
        String type = task.getType();
        Integer merchantId = task.getMerchantId();
        log.info(MessageFormat.format("【定时任务】开始处理数据魔盒回调任务，任务id：{0}，订单id：{1}，任务类型：{2}", taskId, orderId,type));
        try {
            ApiResult result;
            if(Constant.YYS.equals(type)){
                result = userCarrierService.queryResult(taskId, orderId, merchantId);
            } else {
                result = dsService.queryResult(taskId, orderId, merchantId);
            }
            if (result.getResultCode().equals(AppCode.SUCCESS.getCode())) {
                asyncTaskRepository.delete(task);
            }
            log.info(MessageFormat.format("【定时任务】结束处理数据魔盒回调任务，处理结果：{0}", result.getResultMsg()));
        } catch (Throwable e) {
            //发生异常的任务失败状态置为true，不再加入轮询
            log.error(MessageFormat.format("【定时任务】处理任务时发生错误，任务id：{0}", task.getTaskId()), e);
            task.setFailure(true);
            asyncTaskRepository.save(task);
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            //发送邮件
//            HybridPageConfig hybridPageConfig = loanMarkConfigProvider.getHybridPageConfig(merchantId);
//            String emails = hybridPageConfig.getReceiveEmailUserName().trim();
//            try {
//                emailService.send(merchantId, emails, "报警信息-"+type
//                        , MessageFormat.format("【定时任务】处理任务时发生错误，任务id：{0}，异常信息：{1}"
//                                , task.getTaskId(), sw.toString()));
//            } catch (Exception e1) {
//                log.error("发送报警邮件发生错误", e1);
//            }
        }
    }
}

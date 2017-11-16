package com.huotu.loanmarket.service.service;

import com.huotu.loanmarket.common.utils.HttpSender;
import com.huotu.loanmarket.common.utils.RandomUtils;
import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanVerifyCode;
import com.huotu.loanmarket.service.repository.LoanVerifyCodeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author allan
 * @date 01/11/2017
 */
@Service
public class VerifyCodeServiceImpl extends AbstractCrudService<LoanVerifyCode, Long> implements VerifyCodeService {
    private static final Log log = LogFactory.getLog(VerifyCodeServiceImpl.class);

    private LoanVerifyCodeRepository verifyCodeRepository;
    private final String url = "http://sapi.253.com/msg/";
    private final String account = "huotukeji";
    private final String pswd = "Txb123456";

    @Autowired
    protected VerifyCodeServiceImpl(LoanVerifyCodeRepository repository) {
        super(repository);
        this.verifyCodeRepository = repository;
    }

    @Override
    public boolean sendCode(String mobile, String message) {
        int code = RandomUtils.nextIntInSection(1000, 9999);
        message = message.replace("{code}", String.valueOf(code));
        if (send(mobile, message)) {
            LoanVerifyCode verifyCode = verifyCodeRepository.findByMobile(mobile);
            if (verifyCode == null) {
                verifyCode = new LoanVerifyCode();
            }
            verifyCode.setCode(String.valueOf(code));
            verifyCode.setMobile(mobile);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime invalidTime = now.plusMinutes(10);
            verifyCode.setSendTime(Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(now));
            verifyCode.setInvalidTime(Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(invalidTime));
            repository.save(verifyCode);
            return true;
        }
        return false;
    }

    @Override
    public boolean send(String mobile, String message) {
        try {
            log.info("发送验证码：start");
            String response = HttpSender.batchSend(url, account, pswd, mobile, message, true, null);
            response = response.replace("\n", ",");
            String[] responseArray = response.split(",");
            log.info("发送验证码：end");
            log.info("result：" + response);
            log.info("sendInfo：" + responseArray);
            return Integer.valueOf(responseArray[1]) == 0;
        } catch (Exception e) {
            log.info("发送信息失败---" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean verifyCode(String mobile, String code) {
        LoanVerifyCode verifyCode = verifyCodeRepository.findByMobile(mobile);
        return verifyCode != null && verifyCode.getCode().equals(code);
    }

    @Override
    public boolean codeCheck(String mobile, String code) {
        LoanVerifyCode verifyCode = verifyCodeRepository.findByMobile(mobile);
        return verifyCode != null && verifyCode.getCode().equals(code);
    }
}

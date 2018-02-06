package com.huotu.loanmarket.service.service.tongdun.impl;

import com.alibaba.fastjson.JSON;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.LocalDateTimeFormatter;
import com.huotu.loanmarket.service.entity.tongdun.TongdunRequestLog;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.TongdunEnum;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.model.tongdun.*;
import com.huotu.loanmarket.service.model.tongdun.report.ReportConstant;
import com.huotu.loanmarket.service.model.tongdun.report.ReportDetailVo;
import com.huotu.loanmarket.service.model.tongdun.report.RiskGroupComparator;
import com.huotu.loanmarket.service.model.tongdun.report.RiskItem;
import com.huotu.loanmarket.service.repository.tongdun.TongdunRequestLogRepository;
import com.huotu.loanmarket.service.service.tongdun.TongdunRequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wm
 */
@Service
public class TongdunRequestLogServiceImpl implements TongdunRequestLogService {
    @Autowired
    private TongdunRequestLogRepository tongdunRequestLogRepository;

    @Override
    public TongdunRequestLog save(TongdunReviewResult reviewResult) {
        TongdunRequestLog log = this.convertReviewResultToRequestLog(reviewResult);
        log = tongdunRequestLogRepository.saveAndFlush(log);
        reviewResult.setId(log.getId());
        return log;
    }

    @Override
    public Page<TongdunRequestLog> findAll(TongdunLogSearchCondition condition) {
        Pageable pageable = new PageRequest(condition.getPageIndex() - 1, condition.getPageSize(), new Sort(Sort.Direction.DESC, "id"));
        Specification<TongdunRequestLog> specification = (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            //predicateList.add(cb.equal(root.get("merchantId").as(Long.class), condition.getMerchantId()));
            //时间范围
            if (!StringUtils.isEmpty(condition.getBeginTime())) {
                predicateList.add(cb.greaterThanOrEqualTo(root.get("applyTime").as(LocalDateTime.class), LocalDateTimeFormatter.toLocalDateTime(condition.getBeginTime())));
            }
            if (!StringUtils.isEmpty(condition.getEndTime())) {
                predicateList.add(cb.lessThanOrEqualTo(root.get("applyTime").as(LocalDateTime.class), LocalDateTimeFormatter.toLocalDateTime(condition.getEndTime())));
            }
            //关键词
            String searchKey = condition.getSearchKey();
            if (!StringUtils.isEmpty(searchKey)) {
                if (condition.getSearchType().equals(TongdunEnum.LogSearchType.ID_NUMBER.getCode())) {
                    predicateList.add(cb.equal(root.get("idNumber").as(String.class), searchKey));
                } else if (condition.getSearchType().equals(TongdunEnum.LogSearchType.MOBILE.getCode())) {
                    predicateList.add(cb.equal(root.get("mobile").as(String.class), searchKey));
                } else if (condition.getSearchType().equals(TongdunEnum.LogSearchType.NAME.getCode())) {
                    predicateList.add(cb.equal(root.get("name").as(String.class), searchKey));
                } else if (condition.getSearchType().equals(TongdunEnum.LogSearchType.REPORT_ID.getCode())) {
                    predicateList.add(cb.equal(root.get("reportId").as(String.class), searchKey));
                } else if (condition.getSearchType().equals(TongdunEnum.LogSearchType.ORDER_ID.getCode())) {
                    predicateList.add(cb.equal(root.get("orderId").as(String.class), searchKey));
                }
            }
            //审核结果
            if (condition.getDecision() != null && condition.getDecision() > -1) {
                String decision = null;
                if (condition.getDecision().equals(TongdunEnum.DecisionType.ACCEPT.getCode())) {
                    decision = "accept";
                } else if (condition.getDecision().equals(TongdunEnum.DecisionType.REJECT.getCode())) {
                    decision = "reject";
                } else if (condition.getDecision().equals(TongdunEnum.DecisionType.REVIEW.getCode())) {
                    decision = "review";
                }
                if (!StringUtils.isEmpty(decision)) {
                    predicateList.add(cb.equal(root.get("finalDecision").as(String.class), decision));
                }
            }
            //分数范围
            if (condition.getMinScore() != null) {
                predicateList.add(cb.greaterThanOrEqualTo(root.get("finalScore").as(Integer.class), condition.getMinScore()));
            }
            if (condition.getMaxScore() != null) {
                predicateList.add(cb.lessThanOrEqualTo(root.get("finalScore").as(Integer.class), condition.getMaxScore()));
            }
            //调用状态
            if (condition.getState() != null && condition.getState() > -1) {
                predicateList.add(cb.equal(root.get("state").as(Integer.class), condition.getState()));
            }
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        };
        return tongdunRequestLogRepository.findAll(specification, pageable);
    }

    @Override
    public TongdunRequestLog findByOrderId(String orderId) {
        return tongdunRequestLogRepository.findFirstByOrderIdOrderByIdDesc(orderId);
    }

    @Override
    public TongdunRequestLog findById(Long id) {
        return tongdunRequestLogRepository.findOne(id);
    }

    @Override
    public TongdunRequestLog findByUserId(Long userId) {
        TongdunRequestLog requestLog = tongdunRequestLogRepository.findFirstByUserIdOrderByIdDesc(userId);
        return requestLog;
    }

    @Override
    public ReportDetailVo getReportDetail(Long id) {
        TongdunRequestLog requestLog = tongdunRequestLogRepository.findOne(id);
        if (requestLog == null) {
            return null;
        }
        if (requestLog.getState() == 0) {
            return null;
        }
        return this.convertRequestLogToReport(requestLog);
    }

    @Override
    public ReportDetailVo convertRequestLogToReport(TongdunRequestLog requestLog) {
        ReportDetailVo reportDetailVo = new ReportDetailVo();
        //报告信息
        PreLoanQueryResponse queryResponse = JSON.parseObject(requestLog.getReportContent(), PreLoanQueryResponse.class);
        Map<String, List<RiskItem>> riskItemsGroup = queryResponse.getRisk_items().stream().collect(Collectors.groupingBy(RiskItem::getGroup));
        //同盾如果没有风险项，分组结果将是空的，但前台仍要展示给用户，这里就要用上默认分组信息了，并且前端需要按默认分组的指定的顺序排序，比如基本信息排在第一个
        //--补足
        ReportConstant.DEFAULT_GROUP_NAME_MAP.forEach((k, v) -> {
            if (!riskItemsGroup.containsKey(k)) {
                riskItemsGroup.put(k, new ArrayList<>());
            }
        });
        //--按规定排序
        Map<String, List<RiskItem>> sortedRiskItemsGroup = new TreeMap<>(new RiskGroupComparator());
        sortedRiskItemsGroup.putAll(riskItemsGroup);

        reportDetailVo.setRiskItems(sortedRiskItemsGroup);
        reportDetailVo.setAddressDetect(queryResponse.getAddress_detect());
        reportDetailVo.setApplyId(queryResponse.getApplication_id());
        reportDetailVo.setFinalDecision(queryResponse.getFinal_decision());
        reportDetailVo.setFinalScore(queryResponse.getFinal_score());
        reportDetailVo.setReportId(queryResponse.getReport_id());
        reportDetailVo.setReportTime(LocalDateTimeFormatter.toStr(parseTimestamp(queryResponse.getReport_time())));
        reportDetailVo.setRiskCount(queryResponse.getRisk_items().size());

        //用户的信息
        PreLoanSubmitRequest submitRequest = null;
        if (!StringUtils.isEmpty(requestLog.getSubmitParams())) {
            try {
                submitRequest = JSON.parseObject(requestLog.getSubmitParams(), PreLoanSubmitRequest.class);
            } catch (Exception e) {
            }
        }
        //如异常就拿冗余的字段充饥下
        if (submitRequest == null) {
            submitRequest = new PreLoanSubmitRequest();
            submitRequest.setName(requestLog.getName());
            submitRequest.setIdNumber(requestLog.getIdNumber());
            submitRequest.setMobile(requestLog.getMobile());
        }
        reportDetailVo.setSubmitRequest(submitRequest);
        return reportDetailVo;
    }

    private TongdunRequestLog convertReviewResultToRequestLog(TongdunReviewResult reviewResult) {
        TongdunRequestLog requestLog = new TongdunRequestLog();
        requestLog.setId(reviewResult.getId());
        requestLog.setOrderId(reviewResult.getOrderId());
        requestLog.setMerchantId(reviewResult.getMerchantId());
        requestLog.setUserId(reviewResult.getUserId());
        requestLog.setState(reviewResult.getState());
        requestLog.setApplyTime(LocalDateTime.now());
        requestLog.setException(reviewResult.getErrorDesc());
        requestLog.setSubmitParams(reviewResult.getRequestPrams());
        requestLog.setName(reviewResult.getName());
        requestLog.setMobile(reviewResult.getMobile());
        requestLog.setIdNumber(reviewResult.getIdNumber());
        //申请的信息
        PreLoanSubmitResponse submitResponse = reviewResult.getSubmitResponse();
        if (submitResponse == null) {
            return requestLog;
        }
        if (!submitResponse.getSuccess()) {
            requestLog.setSubmitReasonCode(submitResponse.getReason_code());
            requestLog.setSubmitReasonDesc(submitResponse.getReason_desc());
            return requestLog;
        }
        requestLog.setReportId(submitResponse.getReport_id());
        PreLoanQueryResponse queryResponse = reviewResult.getQueryResponse();
        if (queryResponse == null || !queryResponse.getSuccess()) {
            return requestLog;
        }
        //报告的信息
        requestLog.setApplyTime(this.parseTimestamp(queryResponse.getApply_time()));
        requestLog.setApplicationId(queryResponse.getApplication_id());
        requestLog.setFinalDecision(queryResponse.getFinal_decision().toLowerCase());
        requestLog.setFinalScore(queryResponse.getFinal_score());
        requestLog.setReportTime(this.parseTimestamp(queryResponse.getReport_time()));
        requestLog.setReportContent(JSON.toJSONString(queryResponse));
        return requestLog;
    }

    private LocalDateTime parseTimestamp(Long milli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneOffset.of("+8"));
    }
}
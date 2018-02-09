package com.huotu.loanmarket.service.service.system.impl;

import com.huotu.loanmarket.service.entity.system.DataStatisticsByDay;
import com.huotu.loanmarket.service.entity.system.DataStatisticsByHour;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import com.huotu.loanmarket.service.model.system.DataStatisticsVo;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.repository.system.DataStatisticsByDayRepository;
import com.huotu.loanmarket.service.repository.system.DataStatisticsByHourRepository;
import com.huotu.loanmarket.service.repository.user.UserRepository;
import com.huotu.loanmarket.service.service.system.DataStatisticsSearcher;
import com.huotu.loanmarket.service.service.system.DataStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author helloztt
 */
@Service
public class DataStatisticsServiceImpl implements DataStatisticsService {
    @Autowired
    private DataStatisticsByHourRepository hourRepository;
    @Autowired
    private DataStatisticsByDayRepository dayRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    private HashMap<Integer, DataStatisticsVo> todayData = new HashMap<>();

    /**
     * 每小时清空一次统计数据
     */
    @Override
    @Scheduled(cron = "0 0 * * * ?")
    public void cleanCount() {
        todayData.clear();
    }

    @Override
    public DataStatisticsVo todayData(int merchantId) {
        if (todayData.containsKey(merchantId)) {
            return todayData.get(merchantId);
        }
        LocalDateTime searchBeginTime = LocalDate.now().atStartOfDay(), searchEndTime = searchBeginTime.plusDays(1);
        //统计区间的hour数据
        DataStatisticsByDay sumByHourData = hourRepository.sumByBeginTime(merchantId, searchBeginTime, searchEndTime);
        //统计总收入和总用户数
        Object[] sumUserAndAmount = dayRepository.sumUserAndAmount(merchantId).get(0);
        DataStatisticsVo statisticsVo = new DataStatisticsVo(sumByHourData
                , sumUserAndAmount[0] != null ? (long) sumUserAndAmount[0] : 0
                , sumUserAndAmount[1] != null ? (BigDecimal) sumUserAndAmount[1] : BigDecimal.ZERO);
        todayData.put(merchantId, statisticsVo);
        return statisticsVo;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void toStatisticsDataByHour(int merchantId) {
        DataStatisticsByHour hourData = new DataStatisticsByHour();
        hourData.setMerchantId(merchantId);
        //开始统计啦~
        //订单数量
        int orderCount = orderRepository.countByMerchantIdAndCreateTime(merchantId, hourData.getBeginTime(), hourData.getEndTime());
        hourData.setOrderCount(orderCount);
        //订单成功支付数量和金额
        Object[] orderCountAndSum = orderRepository.sumByMerchantIdAndPayTime(merchantId, hourData.getBeginTime(), hourData.getEndTime()).get(0);
        if (orderCountAndSum != null) {
            hourData.setOrderPayCount(orderCountAndSum[0] != null ? ((Long) orderCountAndSum[0]).intValue() : 0);
            if (orderCountAndSum[1] != null) {
                hourData.setOrderAmount((BigDecimal) orderCountAndSum[1]);
            }
        }
        //订单数量
        //新增用户人数
        int userCount = userRepository.countByMerchantIdAndRegTime(merchantId, hourData.getBeginTime(), hourData.getEndTime());
        hourData.setUserCount(userCount);
        //认证成功失败数量
        int authSuccessCount = orderRepository.countByAuthStatusAndPayTime(merchantId, UserAuthorizedStatusEnums.AUTH_SUCCESS, hourData.getBeginTime(), hourData.getEndTime());
        int authFailureCount = orderRepository.countByAuthStatusAndPayTime(merchantId, UserAuthorizedStatusEnums.AUTH_ERROR, hourData.getBeginTime(), hourData.getEndTime());
        hourData.setAuthSuccessCount(authSuccessCount);
        hourData.setAuthFailureCount(authFailureCount);
        hourRepository.save(hourData);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void toStatisticsDataByDay(int merchantId) {
        LocalDateTime searchEndTime = LocalDate.now().atStartOfDay(), searchBeginTime = searchEndTime.minusDays(1);
        //统计区间的hour数据
        DataStatisticsByDay sumByHourData = hourRepository.sumByBeginTime(merchantId, searchBeginTime, searchEndTime);
        sumByHourData.setMerchantId(merchantId);
        dayRepository.save(sumByHourData);
    }

    @Override
    public Page<DataStatisticsByDay> getDayReport(DataStatisticsSearcher searcher) {
        return dayRepository.findAll((root, cq, cb) -> {
            List<Predicate> conditionList = new ArrayList<>();
            if (searcher.getMerchantId() != null && searcher.getMerchantId() > 0) {
                conditionList.add(cb.equal(root.get("merchantId"), searcher.getMerchantId()));
            }
            if (searcher.getBeginDate() != null) {
                conditionList.add(cb.greaterThanOrEqualTo(root.get("beginTime"), searcher.getBeginDate()));
            }
            if (searcher.getEndDate() != null) {
                conditionList.add(cb.lessThanOrEqualTo(root.get("endTime"), searcher.getEndDate()));
            }
            return cb.and(conditionList.toArray(new Predicate[conditionList.size()]));
        }, new PageRequest(searcher.getPageIndex() - 1, searcher.getPageSize(), new Sort(Sort.Direction.DESC, "dataId")));
    }
}

/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.order.impl;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.utils.RandomUtils;
import com.huotu.loanmarket.common.utils.StringUtilsExt;
import com.huotu.loanmarket.service.aop.BusinessSafe;
import com.huotu.loanmarket.service.config.LoanMarkConfigProvider;
import com.huotu.loanmarket.service.config.SesameSysConfig;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.entity.order.OrderLog;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.ConfigParameter;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import com.huotu.loanmarket.service.model.order.ApiCheckoutResultVo;
import com.huotu.loanmarket.service.model.order.ApiOrderInfoVo;
import com.huotu.loanmarket.service.model.order.OrderSearchCondition;
import com.huotu.loanmarket.service.model.order.OrderThirdUrlInfo;
import com.huotu.loanmarket.service.model.order.PayReturnVo;
import com.huotu.loanmarket.service.model.order.SubmitOrderInfo;
import com.huotu.loanmarket.service.model.payconfig.ApiPaymentVo;
import com.huotu.loanmarket.service.model.sesame.BizParams;
import com.huotu.loanmarket.service.model.sesame.IdentityParam;
import com.huotu.loanmarket.service.model.sesame.SesameConfig;
import com.huotu.loanmarket.service.repository.order.OrderLogRepository;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.BaseService;
import com.huotu.loanmarket.service.service.merchant.MerchantCfgService;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.service.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author guomw
 * @date 01/02/2018
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Log log = LogFactory.getLog(OrderServiceImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private MerchantCfgService merchantCfgService;
    @Autowired
    private OrderLogRepository orderLogRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private LoanMarkConfigProvider loanMarkConfigProvider;
    @Autowired
    private BaseService baseService;
    @Autowired
    private EntityManager entityManager;

    /**
     * 创建订单
     *
     * @param submitOrderInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @BusinessSafe
    @Override
    public Order create(SubmitOrderInfo submitOrderInfo) {
        return submitOrder(submitOrderInfo);
    }

    /**
     * 确认订单
     *
     * @param submitOrderInfo
     * @return
     */
    @Override
    public ApiCheckoutResultVo checkout(SubmitOrderInfo submitOrderInfo) {
        Order order = getTradeOrder(submitOrderInfo);

        ApiCheckoutResultVo checkoutResultVo = new ApiCheckoutResultVo();

        checkoutResultVo.setUserId(submitOrderInfo.getUserId());
        checkoutResultVo.setFinalAmount(order.getPayAmount());
        checkoutResultVo.setTradeName(submitOrderInfo.getOrderType().getName());

        // TODO: 02/02/2018 目前只一种支付方式
        List<ApiPaymentVo> availablePaymentList = new ArrayList<>();
        ApiPaymentVo apiPaymentVo = new ApiPaymentVo();
        apiPaymentVo.setName(OrderEnum.PayType.ALIPAY.getName());
        apiPaymentVo.setPayType(OrderEnum.PayType.ALIPAY.getCode());
        apiPaymentVo.setRemark("");
        availablePaymentList.add(apiPaymentVo);
        checkoutResultVo.setPayments(availablePaymentList);
        return checkoutResultVo;
    }

    @Override
    public PayReturnVo getPayReturnInfo(String orderNo) {
        Order unifiedOrder = this.findByOrderId(orderNo);
        if (unifiedOrder == null) {
            return null;
        }
        PayReturnVo payReturnVo = new PayReturnVo();
        OrderThirdUrlInfo urlInfo = this.getOrderThirdUrl(unifiedOrder);
        payReturnVo.setRedirectText(urlInfo.getBtnReturnTitle());
        payReturnVo.setRedirectUrl(urlInfo.getUrl());
        payReturnVo.setUserId(unifiedOrder.getUser().getUserId().intValue());
        payReturnVo.setUnifiedOrderNo(orderNo);
        return payReturnVo;
    }

    /**
     * 提交订单
     *
     * @param submitOrderInfo
     * @return
     */
    private Order submitOrder(SubmitOrderInfo submitOrderInfo) {
        User user = userService.findByMerchantIdAndUserId(Constant.MERCHANT_ID, submitOrderInfo.getUserId());
        Order order = getTradeOrder(submitOrderInfo);
        order.setUser(user);
        order.setOrderId(RandomUtils.randomDateTimeString(6));
        order.setPayType(submitOrderInfo.getPayType());
        //设置第三方授权页面地址
        OrderThirdUrlInfo thirdUrlInfo = getOrderThirdUrl(order);
        order.setThirdAuthUrl(thirdUrlInfo.getUrl());
        order = orderRepository.save(order);

        OrderLog log = new OrderLog();
        log.setLogType(OrderEnum.LogType.CREATE_ORDER);
        log.setLogText("创建订单");
        log.setMerchant(order.getMerchant());
        log.setOpName(order.getUser().getUserName());
        log.setOrderId(order.getOrderId());
        log.setUserId(submitOrderInfo.getUserId());
        log.setResult(1);
        log.setActTime(LocalDateTime.now());
        orderLogRepository.save(log);
        return order;
    }

    /***
     * 封装交易订单信息
     * @param submitOrderInfo
     * @return
     */
    private Order getTradeOrder(SubmitOrderInfo submitOrderInfo) {
        Order order = new Order();
        order.setMobile(submitOrderInfo.getMobile());
        order.setRealName(submitOrderInfo.getName());
        order.setIdCardNo(submitOrderInfo.getIdCardNo());
        order.setOrderType(submitOrderInfo.getOrderType());
        order.setCreateTime(LocalDateTime.now());
        Map<String, String> items = merchantCfgService.getConfigItem(Constant.MERCHANT_ID, MerchantConfigEnum.AUTH_FEE);
        String money = "10";
        switch (submitOrderInfo.getOrderType()) {
            //行业黑名单
            case BACKLIST_BUS:
                money = items.get(ConfigParameter.AuthFeeParameter.BACKLIST_BUS_FEE.getKey());
                break;
            //金融黑名单
            case BACKLIST_FINANCE:
                money = items.get(ConfigParameter.AuthFeeParameter.BACKLIST_FINANCE_FEE.getKey());
                break;
            //运营商
            case CARRIER:
                money = items.get(ConfigParameter.AuthFeeParameter.CARRIER_FEE.getKey());
                break;
            //淘宝
            case TAOBAO:
                money = items.get(ConfigParameter.AuthFeeParameter.TAOBAO_FEE.getKey());
                break;
            //京东
            case JINGDONG:
                money = items.get(ConfigParameter.AuthFeeParameter.JINGDONG_FEE.getKey());
                break;
            default:
                break;
        }
        if (money == null || StringUtils.isEmpty(money)) {
            money = "10";
        }
        ;
        order.setPayAmount(new BigDecimal(money));
        return order;
    }


    @Override
    public Order findByOrderId(String orderId) {
        return orderRepository.findOne(orderId);
    }

    /**
     * 获取订单列表
     *
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @param authStatus
     * @return
     */
    @Override
    public List<ApiOrderInfoVo> getList(Long userId, int pageIndex, int pageSize, @RequestParam(required = false) UserAuthorizedStatusEnums authStatus) {
        List<ApiOrderInfoVo> result = new ArrayList<>();
        Pageable pageable = new PageRequest(pageIndex - 1, pageSize, new Sort(Sort.Direction.DESC, "createTime"));
        Page<Order> page = orderRepository.findAll(getOrderSpecification(userId, authStatus), pageable);

        page.getContent().forEach(order -> {
            ApiOrderInfoVo apiOrderInfoVo = new ApiOrderInfoVo();
            apiOrderInfoVo.setCreateTime(order.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            apiOrderInfoVo.setOrderId(order.getOrderId());
            apiOrderInfoVo.setOrderName(order.getOrderType().getName() + "风险检测");

            OrderEnum.ApiOrderStatus status = getApiOrderStatus(order);
            apiOrderInfoVo.setStatus(status.getCode());
            apiOrderInfoVo.setStatusName(status.getName());
            if (status.equals(OrderEnum.ApiOrderStatus.AUTH_ING) || status.equals(OrderEnum.ApiOrderStatus.AUTH_SUCCESS)) {
                OrderThirdUrlInfo thirdUrlInfo = getOrderThirdUrl(order);
                apiOrderInfoVo.setThirdAuthUrl(thirdUrlInfo.getUrl());
            }
            String desc = "";
            switch (order.getOrderType()) {
                case BACKLIST_BUS:
                case BACKLIST_FINANCE:
                    desc = MessageFormat.format("{0}({1})", order.getRealName(), StringUtilsExt.safeGetIdCardNo(order.getIdCardNo()));
                    break;
                case CARRIER:
                    desc = MessageFormat.format("({0})", StringUtilsExt.safeGetMobile(order.getMobile()));
                    break;
                case TAOBAO:
                case JINGDONG:
                    if (order.getAuthStatus().equals(UserAuthorizedStatusEnums.AUTH_SUCCESS)) {
                        desc = MessageFormat.format("账户:{0}", order.getAccountNo());
                    }
                    break;
                default:
                    break;
            }

            apiOrderInfoVo.setDesc(desc);
            result.add(apiOrderInfoVo);
        });
        return result;
    }

    /**
     * 获取订单状态
     *
     * @param order
     * @return
     */
    private OrderEnum.ApiOrderStatus getApiOrderStatus(Order order) {
        OrderEnum.ApiOrderStatus status = OrderEnum.ApiOrderStatus.CANCEL;

        if (order == null) {
            return status;
        }
        if (order.getOrderStatus().equals(OrderEnum.OrderStatus.CANCEL)) {
            return OrderEnum.ApiOrderStatus.CANCEL;
        }
        if (order.getPayStatus().equals(OrderEnum.PayStatus.NOT_PAY)) {
            return OrderEnum.ApiOrderStatus.NOT_PAY;
        }
        if (order.getPayStatus().equals(OrderEnum.PayStatus.PAY_SUCCESS)) {
            if (order.getAuthStatus().equals(UserAuthorizedStatusEnums.AUTH_ING)) {
                return OrderEnum.ApiOrderStatus.AUTH_ING;
            }
            if (order.getAuthStatus().equals(UserAuthorizedStatusEnums.AUTH_SUCCESS)) {
                return OrderEnum.ApiOrderStatus.AUTH_SUCCESS;
            }
            if (order.getAuthStatus().equals(UserAuthorizedStatusEnums.AUTH_ERROR)) {
                return OrderEnum.ApiOrderStatus.AUTH_ERROR;
            }
        }
        return status;
    }

    /**
     * 获取筛选条件
     *
     * @param userId
     * @return
     */
    private Specification<Order> getOrderSpecification(Long userId) {
        return getOrderSpecification(userId, null, null, null, null);
    }

    /**
     * 获取筛选条件
     *
     * @param userId
     * @param authorizedStatusEnums
     * @return
     */
    private Specification<Order> getOrderSpecification(Long userId, UserAuthorizedStatusEnums authorizedStatusEnums) {
        return getOrderSpecification(userId, null, null, null, authorizedStatusEnums);
    }

    /**
     * 获取筛选条件
     *
     * @param userId
     * @param payStatus
     * @param orderType
     * @param orderStatus
     * @param authStatus
     * @return
     */
    private Specification<Order> getOrderSpecification(Long userId,
                                                       OrderEnum.PayStatus payStatus,
                                                       OrderEnum.OrderType orderType,
                                                       OrderEnum.OrderStatus orderStatus,
                                                       UserAuthorizedStatusEnums authStatus) {
        Specification<Order> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("user").get("userId").as(Long.class), userId));
            //认证状态
            if (authStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("authStatus").as(UserAuthorizedStatusEnums.class), authStatus));
            }
            if (payStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("payStatus").as(OrderEnum.PayStatus.class), payStatus));
            }
            if (orderType != null) {
                predicates.add(criteriaBuilder.equal(root.get("orderType").as(OrderEnum.OrderType.class), orderType));
            }
            if (orderStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("orderStatus").as(OrderEnum.OrderStatus.class), orderStatus));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return specification;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderPayStatus(String orderId, OrderEnum.PayStatus payStatus) {
        orderRepository.updateOrderPayStatusByOrderId(orderId, payStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(String orderId, OrderEnum.OrderStatus orderStatus) {
        orderRepository.updateOrderStatusByOrderId(orderId, orderStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderAuthStatus(String orderId, UserAuthorizedStatusEnums authStatus) {
        orderRepository.updateOrderAuthStatusByOrderId(orderId, authStatus);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderAuthCount(String orderId) {
        orderRepository.updateOrderAuthCountByOrderId(orderId);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order save(Order order) {
        return orderRepository.save(order);
    }


    /***
     * 获取订单第三方来链接
     * @param order
     * @return
     */
    @Override
    public OrderThirdUrlInfo getOrderThirdUrl(Order order) {
        OrderThirdUrlInfo orderThirdUrlInfo = new OrderThirdUrlInfo();
        if (order == null) {
            return orderThirdUrlInfo;
        }
        //订单所有的状态 状态0已取消 1待支付 2认证中 3认证成功 4认证失败
        OrderEnum.ApiOrderStatus orderStatus = getApiOrderStatus(order);
        String homeURI = baseService.apiHomeURI();
        String url = "";
        switch (order.getOrderType()) {
            case BACKLIST_FINANCE:
                if (orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_ING)
                        || orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_SUCCESS)
                        || orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_ERROR)) {
                    url = homeURI + "report/financialBlack?userId=" + order.getUser().getUserId() + "&orderId=" + order.getOrderId();
                }
                break;
            case CARRIER:
                if (orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_SUCCESS)) {
                    url = homeURI + "api/carrier/carrierShow?userId=" + order.getUser().getUserId() + "&orderId=" + order.getOrderId();
                }
                if (orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_ING)) {
                    url = String.format("https://open.shujumohe.com/box/yys?box_token=5884F7B994A7445E9B6C89CA2D2942AA&real_name=%s&identity_code=%s&user_mobile=%s&passback_params=%s", order.getRealName(), order.getIdCardNo(), order.getMobile(), order.getOrderId() + ",1," + Constant.YYS);
                }

                break;
            case JINGDONG:
                if (orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_SUCCESS)) {
                    url = homeURI + "api/ds/dsShow?userId=" + order.getUser().getUserId() + "&orderId=" + order.getOrderId();
                }
                if (orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_ING)) {
                    url = String.format("https://open.shujumohe.com/box/jd?box_token=5884F7B994A7445E9B6C89CA2D2942AA&passback_params=%s", order.getOrderId() + ",1," + Constant.DS);
                }
                break;
            case TAOBAO:
                // 淘宝不支持h5对接。
                if (orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_SUCCESS)) {
                    url = homeURI + "api/ds/dsShow?userId=" + order.getUser().getUserId() + "&orderId=" + order.getOrderId();
                }
                if (orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_ING)) {
                    url = "gh_credit://authTaobao";
                }
                break;
            case BACKLIST_BUS:
                if (orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_SUCCESS)) {
                    url = homeURI + "/api/sesameReport/getSesameReport?userId=" + order.getUser().getUserId() + "&orderId=" + order.getOrderId();
                }
                if (orderStatus.equals(OrderEnum.ApiOrderStatus.AUTH_ING)) {
                    ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
                    // 必要参数
                    req.setChannel("apppc");
                    req.setPlatform("zmop");
                    req.setIdentityType("2");
                    IdentityParam identityParam = new IdentityParam();
                    identityParam.setCertNo(order.getIdCardNo());
                    identityParam.setName(order.getRealName());
                    req.setIdentityParam(identityParam.toString());
                    BizParams bizParams = new BizParams();
                    bizParams.setState(order.getUser().getUserId() + "," + order.getOrderId());
                    req.setBizParams(bizParams.toString());
                    //读取系统参数
                    SesameConfig sesameConfig = loanMarkConfigProvider.getSesameConfig(order.getMerchant());
                    DefaultZhimaClient client = new DefaultZhimaClient(SesameSysConfig.SESAME_CREDIT_URL, sesameConfig.getAppId(),
                            sesameConfig.getPrivateKey().trim(),
                            sesameConfig.getPublicKey().trim());
                    try {
                        url = client.generatePageRedirectInvokeUrl(req);
                    } catch (ZhimaApiException e) {
                        log.info("芝麻行业黑名单授权异常" + e);
                    }
                }
                break;
            default:
                break;
        }
        orderThirdUrlInfo.setUrl(url);
        return orderThirdUrlInfo;
    }

    /**
     * 完成支付
     *
     * @param unifiedOrder
     * @param user
     */
    @Override
    public void paid(Order unifiedOrder, User user) {
        //0、检查支付金额是不是足额
        if (unifiedOrder.getOnlineAmount().compareTo(unifiedOrder.getPayAmount()) == -1) {
            log.error(MessageFormat.format("统一支付订单异常：需付：{0}，实付：{1}，单号：{2}，交易号：{3}",
                    unifiedOrder.getPayAmount(), unifiedOrder.getOnlineAmount(), unifiedOrder.getOrderId(), unifiedOrder.getTradeNo()));
            return;
        }
        //1、更改订单状态
        unifiedOrder.setPayTime(LocalDateTime.now());
        unifiedOrder.setPayStatus(OrderEnum.PayStatus.PAY_SUCCESS);
        unifiedOrder.setAuthStatus(UserAuthorizedStatusEnums.AUTH_ING);
        //TODO:系统配置读取
        unifiedOrder.setAuthCount(3);
        orderRepository.save(unifiedOrder);
    }

    @Override
    public Page<Order> findAll(OrderSearchCondition condition) {
        Specification<Order> specification = getOrderSpecification(condition);

        return orderRepository.findAll(specification, new PageRequest(condition.getPageIndex() - 1, condition.getPageSize()));
    }

    @Override
    public BigDecimal sumPayAmount(OrderSearchCondition orderSearchCondition) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Order> root = query.from(Order.class);
        query.where(getPredicate(orderSearchCondition, root, cb));
        query.select(cb.tuple(cb.sum(root.get("payAmount"))));
        TypedQuery<Tuple> q = entityManager.createQuery(query);
        List<Tuple> result = q.getResultList();
        Tuple tuple = result.get(0);
        return (BigDecimal) tuple.get(0);
    }

    private Specification<Order> getOrderSpecification(OrderSearchCondition condition) {
        return (root, query, cb) -> getPredicate(condition, root, cb);
    }

    private Predicate getPredicate(OrderSearchCondition condition, Root<Order> root, CriteriaBuilder cb) {
        List<Predicate> predicateList = new ArrayList<>();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(condition.getUserName())) {
            predicateList.add(cb.equal(root.get("user").get("userName").as(String.class), condition.getUserName()));
        }
        if (condition.getOrderType() != null && condition.getOrderType() >= 0) {
            predicateList.add(cb.equal(root.get("orderType").as(OrderEnum.OrderType.class)
                    , EnumHelper.getEnumType(OrderEnum.OrderType.class, condition.getOrderType())));
        }

        if (condition.getAuthStatus() != null && condition.getAuthStatus() >= 0) {
            predicateList.add(cb.equal(root.get("authStatus").as(UserAuthorizedStatusEnums.class)
                    , EnumHelper.getEnumType(UserAuthorizedStatusEnums.class, condition.getAuthStatus())));
        }

        if (condition.getPayStatus() != null && condition.getPayStatus() >= 0) {
            predicateList.add(cb.equal(root.get("payStatus").as(OrderEnum.PayStatus.class)
                    , EnumHelper.getEnumType(OrderEnum.PayStatus.class, condition.getPayStatus())));
        }

        if (condition.getPayTimeBegin() != null) {
            predicateList.add(cb.and(cb.greaterThanOrEqualTo(root.get("payTime").as(LocalDateTime.class)
                    , condition.getPayTimeBegin().atStartOfDay())));
        }
        if (condition.getPayTimeEnd() != null) {
            predicateList.add(cb.and(cb.lessThanOrEqualTo(root.get("payTime").as(LocalDateTime.class)
                    , condition.getPayTimeEnd().atStartOfDay())));
        }

        if (condition.getCreateTimeBegin() != null) {
            predicateList.add(cb.and(cb.greaterThanOrEqualTo(root.get("createTime").as(LocalDateTime.class)
                    , condition.getCreateTimeBegin().atStartOfDay())));
        }
        if (condition.getCreateTimeEnd() != null) {
            predicateList.add(cb.and(cb.lessThanOrEqualTo(root.get("createTime").as(LocalDateTime.class)
                    , condition.getCreateTimeEnd().atStartOfDay())));
        }

        if (condition.getAuthTimeBegin() != null) {
            predicateList.add(cb.and(cb.greaterThanOrEqualTo(root.get("authTime").as(LocalDateTime.class)
                    , condition.getCreateTimeBegin().atStartOfDay())));
        }
        if (condition.getAuthTimeEnd() != null) {
            predicateList.add(cb.and(cb.lessThanOrEqualTo(root.get("authTime").as(LocalDateTime.class)
                    , condition.getAuthTimeEnd().atStartOfDay())));
        }

        return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
    }


}

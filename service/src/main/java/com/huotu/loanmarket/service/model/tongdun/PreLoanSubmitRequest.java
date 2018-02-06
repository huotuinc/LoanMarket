package com.huotu.loanmarket.service.model.tongdun;

import com.huotu.loanmarket.common.utils.ClassUtils;
import com.huotu.loanmarket.common.utils.ClassUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 贷前检查请求实体
 * 同盾的可请求字段太多，该类挑选我们需要的字段，通多注解映射
 * @author wm
 */
@Getter
@Setter
public class PreLoanSubmitRequest implements Serializable {
    /**
     * 申请借款金额
     */
    @TongdunField(name = "loan_amount")
    private BigDecimal loanAmount;
    /**
     * 申请借款期限
     */
    @TongdunField(name = "loan_term")
    private Integer loanTerm;
    /**
     * 申请借款期限单位,DAY，MONTH
     */
    @TongdunField(name = "loan_term_unit")
    private String loanTermUnit;
    /**
     * 姓名
     */
    @TongdunField(name = "name")
    private String name;
    /**
     * 身份证号
     */
    @TongdunField(name = "id_number")
    private String idNumber;
    /**
     * 手机号
     */
    @TongdunField(name = "mobile")
    private String mobile;
    /**
     * 银行卡
     */
    @TongdunField(name = "card_number")
    private String cardNumber;
    /**
     * QQ
     */
    @TongdunField(name = "qq")
    private String qq;
    /**
     * 邮箱
     */
    @TongdunField(name = "email")
    private String email;
    /**
     * 身份证是否已实名
     */
    @TongdunField(name = "is_id_checked")
    private Boolean isIdChecked = true;
    /**
     * IP地址
     */
    @TongdunField(name = "ip_address")
    private String ipAddress;

    /**
     * 第一联系人社会关系,father,mother,spouse,child,other_relative,friend,coworker,others
     */
    @TongdunField(name = "contact1_relation")
    private String contact1Relation;
    /**
     * 第一联系人姓名
     */
    @TongdunField(name = "contact1_name")
    private String contact1Name;
    /**
     * 第一联系人手机号
     */
    @TongdunField(name = "contact1_mobile")
    private String contact1Mobile;

    /**
     * 第二联系人社会关系,father,mother,spouse,child,other_relative,friend,coworker,others
     */
    @TongdunField(name = "contact2_relation")
    private String contact2Relation;
    /**
     * 第二联系人姓名
     */
    @TongdunField(name = "contact2_name")
    private String contact2Name;
    /**
     * 第二联系人手机号
     */
    @TongdunField(name = "contact2_mobile")
    private String contact2Mobile;
    /**
     * 转换成POST的参数
     *
     * @return
     */
    @Override
    public String toString() {
        Map<String, String> params = new LinkedHashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            TongdunField tongdunField = field.getAnnotation(TongdunField.class);
            String tdFieldName = tongdunField != null ? tongdunField.name() : field.getName();
            Object fieldValue = ClassUtils.getValueByFieldName(field.getName(), this);
            if (!StringUtils.isEmpty(fieldValue)) {
                params.put(tdFieldName, fieldValue.toString());
            }
        }
        StringBuilder postBody = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            try {
                postBody.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
            } catch (UnsupportedEncodingException e) {
            }
        }
        if (!params.isEmpty()) {
            postBody.deleteCharAt(postBody.length() - 1);
        }
        return postBody.toString();
    }

    /* 【可用字段】
        loan_amount", "10000" // 申请借款金额
        loan_term", "12" // 申请借款期限
        loan_term_unit", "DAY" // 期限单位
        loan_date", "2018-11-11" // 申请借款日期
        purpose // 借款用途
        apply_province // 进件省份
        apply_city // 进件城市
        apply_channel // 进件渠道
        name", "皮晴晴" // 姓名
        id_number", "370404199006301915" // 身份证号
        mobile", "13333333333" // 手机号
        card_number", "6230901807030310952" // 银行卡
        work_phone // 单位座机
        home_phone // 家庭座机
        qq // qq
        email", "893939741@qq.com" // 电子邮箱
        diploma // 学历
        marriage // 婚姻
        house_property // 房产情况
        house_type // 房产类型
        registered_address // 户籍地址
        home_address // 家庭地址
        contact_address // 通讯地址
        career // 职业
        applyer_type // 申请人类别
        work_time // 工作时间
        company_name // 工作单位
        company_address // 单位地址
        company_industry // 公司行业
        company_type // 公司性质
        occupation // 职位
        annual_income // 年收入
        is_cross_loan // 三个月内是否跨平台申请借款
        cross_loan_count // 三个月内跨平台申请借款平台个数
        is_liability_loan // 三个月内是否跨平台借款负债
        liability_loan_count // 三个月内跨平台借款负债平台个数
        is_id_checked // 是否通过实名认证
        contact1_relation // 第一联系人社会关系
        concatc1_name // 第一联系人姓名
        contact1_id_number // 第一联系人身份证
        contact1_mobile // 第一联系人手机号
        contact1_addr // 第一联系人家庭地址
        contact1_com_name // 第一联系人工作单位
        contact1_com_addr // 第一联系人工作地址
        contact2_relation
        contact2_name
        contact2_id_number
        contact2_mobile
        contact2_addr
        contact2_com_name
        contact2_com_addr
        contact3_relation
        contact3_name
        contact3_id_number
        contact3_mobile
        contact3_addr
        contact3_com_name
        contact3_com_addr
        contact4_relation
        contact4_name
        contact4_id_number
        contact4_mobile
        contact4_addr
        contact4_com_name
        contact4_com_addr
        contact5_relation
        contact5_name
        contact5_id_number
        contact5_mobile
        contact5_addr
        contact5_com_name
        contact5_com_addr
        ip_address // IP地址
        token_id // token_id
        black_box // black_box
        */
}
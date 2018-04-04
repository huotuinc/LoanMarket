/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.user;

import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.model.user.UserInfoVo;

/**
 * 有信用户相关接口
 * @author guomw
 * @date 2018/3/20
 */
public interface YouXinUserService {


    /**
     * 同步用户
     * @param mobile
     * @param zmfScoreType 1:550以下   2:550~600  3:600~650  4:650以上
     * @return
     * @throws  ErrorMessageException
     */
    UserInfoVo syncUser(String mobile,int zmfScoreType)  throws ErrorMessageException;
}

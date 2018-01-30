/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.repository.user;

import com.huotu.loanmarket.service.entity.user.Invite;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hot
 * @date 2018/1/18
 */
@Repository
public interface InviteRepository extends JpaRepository<Invite,Long>,JpaSpecificationExecutor<Invite> {

    @Query("select i from Invite  i where i.inviterId=?1 and i.authStatus=?2")
    Page<Invite> findByInviterId(Long inviterId, UserAuthorizedStatusEnums authStatus, Pageable pageable);

    @Query("select i from Invite  i where i.userId=?1")
    Invite findByUserId(Long userId);
}

package com.huotu.loanmarket.service.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huotu.loanmarket.service.entity.user.User;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理后台用户列表模型
 *
 * @author helloztt
 */
@Data
public class UserListVo {
    private long userId;
    private String userName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regTime;
    /**
     * 邀请人数
     */
    private int inviteCount;
    /**
     * 订单数量
     */
    private int orderCount;

    public UserListVo(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.regTime = user.getRegTime();
    }
}

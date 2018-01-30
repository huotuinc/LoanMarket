package com.huotu.loanmarket.service.entity.carrier;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 异步任务
 * @author luyuanyuan on 2017/12/27.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_async_task")
public class AsyncTask {

    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * 任务id
     */
    @Column(name = "task_id")
    private String taskId;

    @Column(name = "merchant_id")
    private Long merchantId;

    /**
     * 是否失败
     */
    @Column(name="failure")
    private boolean failure;
}

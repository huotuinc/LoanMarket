package com.huotu.loanmarket.service.repository.carrier;

import com.huotu.loanmarket.service.entity.carrier.AsyncTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author luyuanyuan on 2017/12/27.
 */
public interface AsyncTaskRepository extends JpaRepository<AsyncTask, Long> {
    List<AsyncTask> findByFailureFalse();

    Page<AsyncTask> findByFailureTrue(Pageable pageable);

    @Transactional(rollbackFor = RuntimeException.class)
    @Modifying(clearAutomatically = true)
    @Query("update AsyncTask b set b.failure = 0 where b.taskId = ?1")
    void updateByTaskId(String taskId);

    @Transactional(rollbackFor = RuntimeException.class)
    @Modifying(clearAutomatically = true)
    @Query("update AsyncTask b set b.failure = 0 where b.failure = 1")
    void updateBatch();

    AsyncTask findByOrderIdAndType(String orderId, String type);
}

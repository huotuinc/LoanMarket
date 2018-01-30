package com.huotu.loanmarket.service.repository.Project;

import com.huotu.loanmarket.service.entity.project.ProjectApplyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hxh
 * @date 2017-10-26
 */
@Repository
public interface ProjectApplyLogRepository extends JpaRepository<ProjectApplyLog, Integer> {
}

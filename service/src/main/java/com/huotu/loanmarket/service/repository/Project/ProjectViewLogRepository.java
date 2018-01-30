package com.huotu.loanmarket.service.repository.Project;

import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.project.ProjectViewLog;
import org.springframework.stereotype.Repository;

/**
 * @author hxh
 * @date 2017-10-26
 */
@Repository
public interface ProjectViewLogRepository extends JpaCrudRepository<ProjectViewLog, Integer> {
}

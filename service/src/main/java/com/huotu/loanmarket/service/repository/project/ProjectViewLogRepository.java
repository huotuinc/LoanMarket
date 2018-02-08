package com.huotu.loanmarket.service.repository.project;

import com.huotu.loanmarket.service.entity.project.ProjectViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hxh
 * @date 2017-10-26
 */
@Repository
public interface ProjectViewLogRepository extends JpaRepository<ProjectViewLog, Integer> {
    List<ProjectViewLog> findByProjectId(int projectId);
}

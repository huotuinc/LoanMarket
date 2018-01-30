package com.huotu.loanmarket.service.repository.Project;

import com.huotu.loanmarket.service.base.JpaCrudRepository;
import com.huotu.loanmarket.service.entity.project.Project;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hxh
 * @date 2017-10-24
 */
@Repository
public interface ProjectRepository extends JpaCrudRepository<Project, Integer> {

    @Query("update Project p set p.isHot=?1 where p.loanId in ?2")
    @Modifying
    void setHot(int isHot, List<Integer> projectIds);

    @Query("update Project p set p.isNew=?1 where p.loanId in ?2")
    @Modifying
    void setNew(int isNew, List<Integer> projectIds);
}

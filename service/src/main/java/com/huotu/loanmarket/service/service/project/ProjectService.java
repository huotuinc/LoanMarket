package com.huotu.loanmarket.service.service.project;

import com.huotu.loanmarket.service.entity.project.Project;
import com.huotu.loanmarket.service.entity.project.ProjectApplyLog;
import com.huotu.loanmarket.service.entity.project.ProjectViewLog;
import com.huotu.loanmarket.service.model.projectsearch.ProjectSearchCondition;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author allan
 * @date 23/10/2017
 */
public interface ProjectService {
    /**
     * 按条件分组查询
     *
     * @param pageIndex       页码，索引从1开始
     * @param pageSize        每页条数
     * @param searchCondition {@link ProjectSearchCondition}
     * @return
     */
    Page<Project> findAll(int pageIndex, int pageSize, ProjectSearchCondition searchCondition);

    /**
     * 按条件查询
     *
     * @param categoryId
     * @param amount
     * @param deadline
     * @return
     */
    List<Project> findAll(int categoryId, double amount, int deadline);

    /**
     * 查询所有
     *
     * @return
     */
    List<Project> findAll();

    /**
     * 获得最热数据
     *
     * @return
     */
    List<Project> getHotProject();

    /**
     * 获得最新数据
     *
     * @return
     */
    List<Project> getNewProject();

    /**
     * 设置热卖属性
     *
     * @param isHot
     * @param projectIdsStr
     */
    void setHot(int isHot, String projectIdsStr);

    /**
     * 设置新品属性
     *
     * @param isNew
     * @param projectIdsStr
     */
    void setNew(int isNew, String projectIdsStr);

    /**
     * 根据类目编号查询
     *
     * @param tag 类目编号
     * @return
     */
    List<Project> findByTag(int tag);

    /**
     * 查询
     *
     * @param projectId
     * @return
     */
    Project findOne(Integer projectId);

    /**
     * 保存
     *
     * @param project
     * @return
     */
    Project save(Project project);

    /**
     * @param userId
     * @param projectId
     * @return
     */
    ProjectViewLog logView(int userId, int projectId);

    ProjectApplyLog logApply(int userId, int projectId);

    /**
     * 查询产品浏览量
     *
     * @param projectId
     * @return
     */
    int countProjectView(int projectId);

    /**
     * 查询产品申请量
     *
     * @param projectId
     * @return
     */
    int countProjectApply(int projectId);
}

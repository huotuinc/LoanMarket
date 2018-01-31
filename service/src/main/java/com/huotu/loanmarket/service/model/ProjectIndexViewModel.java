package com.huotu.loanmarket.service.model;

import com.huotu.loanmarket.service.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hxh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectIndexViewModel {
    List<Project> hotProjectList;
    List<Project> newProjectList;
}

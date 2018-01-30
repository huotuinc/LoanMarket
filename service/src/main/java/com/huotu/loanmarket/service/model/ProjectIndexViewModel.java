package com.huotu.loanmarket.service.model;

import com.huotu.loanmarket.service.entity.project.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectIndexViewModel {
    List<Project> hotProjectList;
    List<Project> newProjectList;

    public List<Project> getHotProjectList() {
        return hotProjectList;
    }

    public void setHotProjectList(List<Project> hotProjectList) {
        this.hotProjectList = hotProjectList;
    }

    public List<Project> getNewProjectList() {
        return newProjectList;
    }

    public void setNewProjectList(List<Project> newProjectList) {
        this.newProjectList = newProjectList;
    }
}

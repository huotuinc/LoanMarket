package com.huotu.loanmarket.web.viewmodel;

import com.huotu.loanmarket.service.entity.LoanProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectIndexViewModel {
    List<LoanProject> hotProjectList;
    List<LoanProject> newProjectList;

    public List<LoanProject> getHotProjectList() {
        return hotProjectList;
    }

    public void setHotProjectList(List<LoanProject> hotProjectList) {
        this.hotProjectList = hotProjectList;
    }

    public List<LoanProject> getNewProjectList() {
        return newProjectList;
    }

    public void setNewProjectList(List<LoanProject> newProjectList) {
        this.newProjectList = newProjectList;
    }
}

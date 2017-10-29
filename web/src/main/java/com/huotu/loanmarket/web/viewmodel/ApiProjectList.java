package com.huotu.loanmarket.web.viewmodel;

import com.huotu.loanmarket.service.entity.LoanProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author allan
 * @date 29/10/2017
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiProjectList {
    private long totalRecord;
    private int pageIndex;
    private int totalPage;
    private int pageSize;
    private List<LoanProject> list;

    public void toApiProjectList(Page<LoanProject> projects) {
        this.totalRecord = projects.getTotalElements();
        this.pageIndex = projects.getNumber() + 1;
        this.pageSize = projects.getSize();
        this.totalPage = projects.getTotalPages();
        this.list = projects.getContent();
    }
}

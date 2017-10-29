package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author allan
 * @date 29/10/2017
 */
@Setter
@Getter
@Embeddable
public class UserAndProject implements Serializable {
    @Column(name = "User_Id")
    private Integer userId;

    @Column(name = "Project_Id")
    private Integer projectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAndProject that = (UserAndProject) o;

        return (userId != null ? userId.equals(that.userId) : that.userId == null) && (projectId != null ? projectId.equals(that.projectId) : that.projectId == null);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (projectId != null ? projectId.hashCode() : 0);
        return result;
    }
}

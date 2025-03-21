package com.easy.store.backend.context.category.domain.model;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private Long createBy;
    private Long updateBy;
    private User user;
    private Account account;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private String state;

    public boolean isValid(Category category) {
        if(category.getName() == null || category.getDescription() == null ||
            user == null || account == null) return false;

        return !category.getName().isEmpty() && !category.getDescription().isEmpty();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageName='" + imageName + '\'' +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                ", user=" + user +
                ", account=" + account +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", state='" + state + '\'' +
                '}';
    }
}

package com.easy.store.backend.context.category.domain.model;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.s3.model.S3File;
import com.easy.store.backend.context.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private S3File image;
    private Long createBy;
    private Long updateBy;
    private User user;
    private Account account;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private String state;
    private List<CategoryHasPaymentType> paymentTypes;

    public boolean isValid() {
        if(name == null || description == null ||
            user == null || account == null) return false;

        return !name.isEmpty() && !description.isEmpty();
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

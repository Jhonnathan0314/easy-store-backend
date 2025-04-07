package com.easy.store.backend.context.category_has_payment_type.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHasPaymentType {

    private CategoryHasPaymentTypeId id;
    private Long phone;
    private String email;
    private String accountNumber;
    private String accountType;
    private String accountBank;
    private String state;

    public boolean isValid() {
        if(id == null) return false;
        return id.getCategoryId() != null && id.getPaymentTypeId() != null;
    }

    @Override
    public String toString() {
        return "CategoryHasPaymentType{" +
                "id=" + id +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", accountBank='" + accountBank + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}

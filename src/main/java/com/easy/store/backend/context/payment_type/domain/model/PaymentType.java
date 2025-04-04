package com.easy.store.backend.context.payment_type.domain.model;

import com.easy.store.backend.context.account.domain.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentType {
    private Long id;
    private String name;
    private Account account;
    private Long createBy;
    private Long updateBy;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private String state;

    public boolean isValid() {
        if(name == null || account == null) return false;

        return !name.isEmpty();
    }

    @Override
    public String toString() {
        return "PaymentType{" +
                "id=" + id +
                ", name=" + name +
                ", account=" + account +
                ", createBy=" + createBy +
                ", updateBy=" + updateBy +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", state=" + state +
                '}';
    }
}

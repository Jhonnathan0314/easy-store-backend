package com.sophie.store.backend.context.payment_type.domain.model;

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
    private Long createBy;
    private Long updateBy;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private String state;

    public boolean isValid(PaymentType paymentType) {
        if(paymentType.getName() == null) return false;

        return !paymentType.getName().isEmpty();
    }
}

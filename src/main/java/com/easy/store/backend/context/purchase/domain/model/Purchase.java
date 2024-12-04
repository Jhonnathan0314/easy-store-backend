package com.easy.store.backend.context.purchase.domain.model;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class Purchase {
    private Long id;
    private User user;
    private PaymentType paymentType;
    private Timestamp date;
    private BigDecimal total;
    private Long createBy;

    public boolean isValid(Purchase purchase) {
        if(purchase.getUser() == null || purchase.getPaymentType() == null) return false;

        return purchase.getUser().getId() != null && purchase.getPaymentType().getId() != null;
    }
}

package com.easy.store.backend.context.purchase.application.dto;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.easy.store.backend.context.user.application.dto.UserCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseUpdateDTO {
    private Long id;
    private Long userId;
    private Long paymentTypeId;
    private Long categoryId;
    private String state;
    private Long updateBy;
}

package com.easy.store.backend.context.purchase.application.dto;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.easy.store.backend.context.user.application.dto.UserCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseGenerateDTO {
    private UserCreateDTO user;
    private PaymentTypeCreateDTO paymentType;
    private Long createBy;
}

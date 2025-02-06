package com.easy.store.backend.context.payment_type.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeCreateDTO {
    private String name;
    private Long createBy;
    private Long accountId;
}

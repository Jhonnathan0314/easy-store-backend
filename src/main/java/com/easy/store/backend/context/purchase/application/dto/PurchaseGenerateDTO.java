package com.easy.store.backend.context.purchase.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseGenerateDTO {
    private Long userId;
    private Long paymentTypeId;
    private Long categoryId;
    private BigDecimal total;
    private String state;
    private Long createBy;
}

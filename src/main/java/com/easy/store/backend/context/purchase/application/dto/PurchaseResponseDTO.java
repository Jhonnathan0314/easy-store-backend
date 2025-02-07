package com.easy.store.backend.context.purchase.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseResponseDTO {
    private Long id;
    private Long userId;
    private Long paymentTypeId;
    private Long categoryId;
    private BigDecimal total;
    private String state;
    private Timestamp creationDate;
}

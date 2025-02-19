package com.easy.store.backend.context.purchase.application.dto;

import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductResponseDTO;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
    private List<PurchaseHasProductResponseDTO> products;
}

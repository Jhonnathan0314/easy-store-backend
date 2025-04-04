package com.easy.store.backend.context.purchase.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseUpdateDTO {
    private Long id;
    private Long paymentTypeId;
    private String state;
    private Long updateBy;
}

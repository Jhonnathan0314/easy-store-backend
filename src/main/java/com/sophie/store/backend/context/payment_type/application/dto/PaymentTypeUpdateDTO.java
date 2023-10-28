package com.sophie.store.backend.context.payment_type.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeUpdateDTO {
    private Long id;
    private String name;
    private Long updateBy;
}

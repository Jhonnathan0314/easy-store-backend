package com.easy.store.backend.context.payment_type.application.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PaymentTypeResponseDTO {
    private Long id;
    private String name;
    private String state;
}

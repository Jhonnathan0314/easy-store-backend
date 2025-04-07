package com.easy.store.backend.context.payment_type.application.dto;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor
public class PaymentTypeDTO {
    private Long id;
    private String name;
    private Long createBy;
    private Long updateBy;
}

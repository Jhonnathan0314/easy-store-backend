package com.sophie.store.backend.context.payment_type.application.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class PaymentTypeDTO {
    private Long id;
    private String name;
    private Long createBy;
    private Long updateBy;
}

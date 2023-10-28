package com.sophie.store.backend.context.payment_type.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeDTO {
    private Long id;
    private String name;
    private Long createBy;
    private Long updateBy;
    private Timestamp creationDate;
    private Timestamp updateDate;
    private String state;
}

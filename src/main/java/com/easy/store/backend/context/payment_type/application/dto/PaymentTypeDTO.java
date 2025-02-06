package com.easy.store.backend.context.payment_type.application.dto;

import com.easy.store.backend.context.account.application.dto.AccountDto;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class PaymentTypeDTO {
    private Long id;
    private String name;
    private AccountDto account;
    private Long createBy;
    private Long updateBy;
}

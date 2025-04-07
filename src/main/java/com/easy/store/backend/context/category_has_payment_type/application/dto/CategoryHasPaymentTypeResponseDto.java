package com.easy.store.backend.context.category_has_payment_type.application.dto;

import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHasPaymentTypeResponseDto {

    private CategoryHasPaymentTypeId id;
    private Long phone;
    private String email;
    private String accountNumber;
    private String accountType;
    private String accountBank;
    private String state;

}

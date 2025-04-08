package com.easy.store.backend.context.category.application.dto;

import com.easy.store.backend.context.category_has_payment_type.application.dto.CategoryHasPaymentTypeResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private Long userId;
    private Long accountId;
    private List<CategoryHasPaymentTypeResponseDto> paymentTypes;
}

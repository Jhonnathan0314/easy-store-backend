package com.easy.store.backend.context.category.application.dto;

import com.easy.store.backend.context.category_has_payment_type.application.dto.CategoryHasPaymentTypeResponseDto;
import com.easy.store.backend.context.s3.model.S3File;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private S3File image;
    private Long userId;
    private Long accountId;
    private List<CategoryHasPaymentTypeResponseDto> paymentTypes;
}

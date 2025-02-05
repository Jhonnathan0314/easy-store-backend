package com.easy.store.backend.context.subcategory.application.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class SubcategoryResponseDTO {
    private Long id;
    private String name;
    private Long categoryId;
}

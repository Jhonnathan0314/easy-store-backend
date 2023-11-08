package com.sophie.store.backend.context.subcategory.application.dto;

import com.sophie.store.backend.context.category.application.dto.CategoryResponseDTO;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class SubcategoryResponseDTO {
    private Long id;
    private String name;
    private CategoryResponseDTO category;
}

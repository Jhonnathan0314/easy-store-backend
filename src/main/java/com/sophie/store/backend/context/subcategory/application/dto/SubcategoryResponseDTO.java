package com.sophie.store.backend.context.subcategory.application.dto;

import com.sophie.store.backend.context.category.application.dto.CategoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryResponseDTO {
    private Long id;
    private String name;
    private CategoryResponseDTO category;
}

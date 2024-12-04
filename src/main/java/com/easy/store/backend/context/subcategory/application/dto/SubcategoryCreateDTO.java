package com.easy.store.backend.context.subcategory.application.dto;

import com.easy.store.backend.context.category.application.dto.CategoryCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryCreateDTO {
    private String name;
    private CategoryCreateDTO category;
    private Long createBy;
}

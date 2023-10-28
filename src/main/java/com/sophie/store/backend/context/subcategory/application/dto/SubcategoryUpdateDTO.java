package com.sophie.store.backend.context.subcategory.application.dto;

import com.sophie.store.backend.context.category.application.dto.CategoryUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubcategoryUpdateDTO {
    private Long id;
    private String name;
    private CategoryUpdateDTO category;
    private Long updateBy;
}

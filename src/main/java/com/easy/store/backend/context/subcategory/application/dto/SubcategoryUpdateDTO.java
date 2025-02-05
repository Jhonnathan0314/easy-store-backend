package com.easy.store.backend.context.subcategory.application.dto;

import com.easy.store.backend.context.category.application.dto.CategoryUpdateDTO;
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
    private Long categoryId;
    private Long updateBy;
}

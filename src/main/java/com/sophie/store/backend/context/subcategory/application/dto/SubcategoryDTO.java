package com.sophie.store.backend.context.subcategory.application.dto;

import com.sophie.store.backend.context.category.application.dto.CategoryDTO;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class SubcategoryDTO {
    private Long id;
    private String name;
    private CategoryDTO category;
    private Long createBy;
    private Long updateBy;
}

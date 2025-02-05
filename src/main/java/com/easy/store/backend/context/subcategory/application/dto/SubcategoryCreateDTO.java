package com.easy.store.backend.context.subcategory.application.dto;

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
    private Long categoryId;
    private Long createBy;
}

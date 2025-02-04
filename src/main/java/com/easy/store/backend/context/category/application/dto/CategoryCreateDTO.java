package com.easy.store.backend.context.category.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDTO {
    private String name;
    private String description;
    private String imageName;
    private Long createBy;
    private Long userId;
    private Long accountId;
}

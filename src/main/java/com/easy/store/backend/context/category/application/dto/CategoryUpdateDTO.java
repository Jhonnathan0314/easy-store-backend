package com.easy.store.backend.context.category.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDTO {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private Long updateBy;
    private Long userId;
    private Long accountId;
}

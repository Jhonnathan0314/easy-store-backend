package com.easy.store.backend.context.category.application.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private Long createBy;
    private Long updateBy;
}

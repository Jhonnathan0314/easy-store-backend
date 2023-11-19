package com.sophie.store.backend.context.category.application.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Long createBy;
    private Long updateBy;
}

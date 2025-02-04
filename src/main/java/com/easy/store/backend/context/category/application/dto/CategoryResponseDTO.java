package com.easy.store.backend.context.category.application.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private Long userId;
    private Long accountId;
}

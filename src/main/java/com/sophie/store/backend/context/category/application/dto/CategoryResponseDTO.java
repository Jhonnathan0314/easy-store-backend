package com.sophie.store.backend.context.category.application.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
}

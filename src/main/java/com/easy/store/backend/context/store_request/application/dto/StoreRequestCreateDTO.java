package com.easy.store.backend.context.store_request.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequestCreateDTO {
    private String storeName;
    private String storeDescription;
}

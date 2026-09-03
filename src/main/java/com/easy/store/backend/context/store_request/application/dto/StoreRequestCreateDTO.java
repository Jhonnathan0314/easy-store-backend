package com.easy.store.backend.context.store_request.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequestCreateDTO {

    @NotBlank(message = "el nombre de la tienda es obligatorio")
    private String storeName;

    @NotBlank(message = "la descripción de la tienda es obligatoria")
    private String storeDescription;
}

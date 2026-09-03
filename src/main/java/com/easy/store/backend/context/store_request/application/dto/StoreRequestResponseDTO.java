package com.easy.store.backend.context.store_request.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class StoreRequestResponseDTO {
    private Long id;
    private Long userId;
    private String username;
    private String storeName;
    private String storeDescription;
    private String status;
    private Long reviewedBy;
    private Timestamp creationDate;
    private Timestamp updateDate;
}

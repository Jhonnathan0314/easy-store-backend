package com.easy.store.backend.context.codes.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeDTO {

    private Long userId;
    private Long code;
    private String action;
    private Timestamp creationDate;

}

package com.easy.store.backend.context.codes.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Code {

    private Long userId;
    private Long code;
    private String action;
    private Timestamp creationDate;

    public boolean isValid(Code code) {
        if(code.userId == null && code.code == null && code.action == null) return false;
        return !action.isEmpty();
    }

}

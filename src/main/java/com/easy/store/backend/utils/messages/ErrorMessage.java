package com.easy.store.backend.utils.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessage {
    private int code;
    private String title;
    private String detail;
}

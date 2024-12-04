package com.easy.store.backend.context.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private String name;
    private String description;
    private String state;

}

package com.easy.store.backend.context.account.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateDto {

    private String name;
    private String description;
    private String imageName;

    @Override
    public String toString() {
        return "AccountDto{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

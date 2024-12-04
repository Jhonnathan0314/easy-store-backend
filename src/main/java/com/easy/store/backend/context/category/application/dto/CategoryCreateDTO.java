package com.easy.store.backend.context.category.application.dto;

import com.easy.store.backend.context.account.application.dto.AccountCreateDto;
import com.easy.store.backend.context.user.application.dto.UserCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateDTO {
    private String name;
    private String description;
    private String imageName;
    private Long createBy;
    private UserCreateDTO user;
    private AccountCreateDto account;
}

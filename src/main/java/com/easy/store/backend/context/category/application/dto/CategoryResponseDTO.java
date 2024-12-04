package com.easy.store.backend.context.category.application.dto;

import com.easy.store.backend.context.account.application.dto.AccountDto;
import com.easy.store.backend.context.user.application.dto.UserResponseDTO;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private UserResponseDTO user;
    private AccountDto account;
}

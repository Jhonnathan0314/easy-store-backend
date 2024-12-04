package com.easy.store.backend.context.category.application.dto;

import com.easy.store.backend.context.account.application.dto.AccountDto;
import com.easy.store.backend.context.user.application.dto.UserDTO;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private String imageName;
    private Long createBy;
    private Long updateBy;
    private UserDTO user;
    private AccountDto account;
}

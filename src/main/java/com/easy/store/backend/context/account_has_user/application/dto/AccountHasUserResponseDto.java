package com.easy.store.backend.context.account_has_user.application.dto;

import com.easy.store.backend.context.account.application.dto.AccountDto;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.user.application.dto.UserDTO;
import com.easy.store.backend.context.user.application.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHasUserResponseDto {

    private AccountHasUserId id;
    private AccountDto account;
    private UserResponseDTO user;
    private String state;

}

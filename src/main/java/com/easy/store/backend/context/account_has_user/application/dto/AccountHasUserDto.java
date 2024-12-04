package com.easy.store.backend.context.account_has_user.application.dto;

import com.easy.store.backend.context.account.application.dto.AccountDto;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.user.application.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHasUserDto {

    private AccountHasUserId id;
    private AccountDto account;
    private UserDTO user;
    private String state;

    @Override
    public String toString() {
        return "AccountHasUserDto{" +
                "id=" + id.toString() +
                ", state='" + state + '\'' +
                '}';
    }
}

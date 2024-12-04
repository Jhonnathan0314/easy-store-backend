package com.easy.store.backend.context.account_has_user.application.dto;

import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHasUserCreateDto {

    private AccountHasUserId id;

    @Override
    public String toString() {
        return "AccountHasUserCreateDto{" +
                "id=" + id.toString() +
                '}';
    }
}

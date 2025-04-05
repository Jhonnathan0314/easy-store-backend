package com.easy.store.backend.context.account_has_user.domain.model;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHasUser {

    private AccountHasUserId id;
    private Account accountId;
    private User userId;
    private String state;

    public boolean isValid() {
        return id.isValid();
    }

    @Override
    public String toString() {
        return "AccountHasUser{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", userId=" + userId +
                ", state=" + state +
                '}';
    }
}

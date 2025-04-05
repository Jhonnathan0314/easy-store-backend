package com.easy.store.backend.context.account_has_user.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountHasUserId implements Serializable {

    private Long userId;
    private Long accountId;

    public boolean isValid() {
        return userId != null && accountId != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountHasUserId that)) return false;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getAccountId(), that.getAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getAccountId());
    }

    @Override
    public String toString() {
        return "AccountHasUserId{" +
                "userId=" + userId +
                ", accountId=" + accountId +
                '}';
    }
}

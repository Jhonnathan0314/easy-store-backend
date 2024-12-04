package com.easy.store.backend.context.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;
    private String name;
    private String description;
    private String state;

    public boolean isValid(Account account) {
        if(account.getName() == null ||
            account.getDescription() == null) return false;

        return !account.getName().isEmpty() && !account.getDescription().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(getName(), account.getName()) && Objects.equals(getDescription(), account.getDescription()) && getState() == account.getState();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getState());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state=" + state +
                '}';
    }
}

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
    private String imageName;

    public boolean isValid() {
        if(name == null ||
            description == null) return false;

        return !name.isEmpty() && !description.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(getId(), account.getId()) && Objects.equals(getName(), account.getName()) && Objects.equals(getDescription(), account.getDescription()) && Objects.equals(getState(), account.getState()) && Objects.equals(getImageName(), account.getImageName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getState(), getImageName());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", state=" + state +
                ", imageName=" + imageName +
                '}';
    }
}

package com.sophie.store.backend.context.user.data;

import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.user.domain.model.User;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class UserData {

    //Correct information
    private final User userActive = User.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .state("active")
            .build();

    private final User userInactive = User.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .state("inactive")
            .build();

    private final User userEmpty = User.builder()
            .id(1L)
            .name("")
            .lastName("")
            .username("")
            .password("")
            .state("")
            .build();

    private final User userCreateValid = User.builder()
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .password("12345")
            .build();

    private final User userCreateNoPassword = User.builder()
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .build();

    private final User userCreateInvalid = User.builder()
            .name("test")
            .lastName("test")
            .password("12345")
            .build();

    private final User userToUpdate = User.builder()
            .id(1L)
            .name("update")
            .lastName("update")
            .username("test@test.com")
            .password("12345")
            .build();

    private final User userToUpdateNoId = User.builder()
            .name("update")
            .lastName("update")
            .username("test@test.com")
            .password("12345")
            .build();

    private final User userToUpdateNoPassword = User.builder()
            .id(1L)
            .name("update")
            .lastName("update")
            .username("test@test.com")
            .build();

    private final User userToUpdateInvalid = User.builder()
            .id(1L)
            .username("test@test.com")
            .password("12345")
            .build();

    private final User userUpdated = User.builder()
            .id(1L)
            .name("update")
            .lastName("update")
            .username("test@test.com")
            .password("12345")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .state("active")
            .build();

    private final User userResponseOne = User.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final User userResponseTwo = User.builder()
            .id(2L)
            .name("test2")
            .lastName("test2")
            .username("test2@test.com")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final String encodedPassword = "12345ENCODED";

    private List<User> usersList;

    public UserData() {
        usersList = new LinkedList<>();
        usersList.add(userResponseOne);
        usersList.add(userResponseTwo);
    }
}

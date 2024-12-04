package com.easy.store.backend.context.user.data;

import com.easy.store.backend.context.roles.application.dto.RoleDTO;
import com.easy.store.backend.context.roles.application.dto.RoleResponseDTO;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.easy.store.backend.context.user.application.dto.UserCreateDTO;
import com.easy.store.backend.context.user.application.dto.UserDTO;
import com.easy.store.backend.context.user.application.dto.UserResponseDTO;
import com.easy.store.backend.context.user.application.dto.UserUpdateDTO;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.infrastructure.persistence.UserEntity;
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

    //To mapper test
    private final UserEntity userEntity = UserEntity.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .password("12345")
            .role(RoleEntity.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .state("active")
            .build();

    private final UserDTO userDTO = UserDTO.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .password("12345")
            .role(RoleDTO.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .password("12345")
            .build();

    private final UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .password("12345")
            .build();

    private final UserResponseDTO userResponseDTO = UserResponseDTO.builder()
            .id(1L)
            .name("test")
            .lastName("test")
            .username("test@test.com")
            .role(RoleResponseDTO.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    private final User userModel = User.builder()
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

    public UserData() {
        usersList = new LinkedList<>();
        usersList.add(userResponseOne);
        usersList.add(userResponseTwo);
    }
}

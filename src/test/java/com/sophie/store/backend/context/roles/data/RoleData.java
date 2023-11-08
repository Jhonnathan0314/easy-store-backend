package com.sophie.store.backend.context.roles.data;

import com.sophie.store.backend.context.roles.application.dto.RoleCreateDTO;
import com.sophie.store.backend.context.roles.application.dto.RoleDTO;
import com.sophie.store.backend.context.roles.application.dto.RoleResponseDTO;
import com.sophie.store.backend.context.roles.application.dto.RoleUpdateDTO;
import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class RoleData {

    //Correct information
    private final Role roleActive = Role.builder()
            .id(1L)
            .name("test")
            .state("active")
            .build();

    private final Role roleInactive = Role.builder()
            .id(1L)
            .name("test")
            .state("inactive")
            .build();

    private final Role roleEmpty = Role.builder()
            .id(1L)
            .name("")
            .state("")
            .build();

    private final Role roleCreateValid = Role.builder()
            .name("test")
            .build();

    private final Role roleCreateInvalid = Role.builder().build();

    private final Role roleToUpdate = Role.builder()
            .id(1L)
            .name("update")
            .build();

    private final Role roleUpdated = Role.builder()
            .id(1L)
            .name("update")
            .build();

    private final Role roleToUpdateNoId = Role.builder()
            .name("update")
            .build();

    private final Role roleToUpdateInvalid = Role.builder()
            .id(1L)
            .build();

    private final Role roleResponseOne = Role.builder()
            .id(1L)
            .name("test")
            .build();

    private final Role roleResponseTwo = Role.builder()
            .id(2L)
            .name("test2")
            .build();

    private final String encodedPassword = "12345ENCODED";

    private List<Role> rolesList;

    //To mapper test
    private final RoleEntity roleEntity = RoleEntity.builder()
            .id(1L)
            .name("test")
            .state("active")
            .build();

    private final RoleDTO roleDTO = RoleDTO.builder()
            .id(1L)
            .name("test")
            .build();

    private final RoleCreateDTO roleCreateDTO = RoleCreateDTO.builder()
            .name("test")
            .build();

    private final RoleUpdateDTO roleUpdateDTO = RoleUpdateDTO.builder()
            .id(1L)
            .name("test")
            .build();

    private final RoleResponseDTO roleResponseDTO = RoleResponseDTO.builder()
            .id(1L)
            .name("test")
            .build();

    private final Role roleModel = Role.builder()
            .id(1L)
            .name("test")
            .state("active")
            .build();

    public RoleData() {
        rolesList = new LinkedList<>();
        rolesList.add(roleResponseOne);
        rolesList.add(roleResponseTwo);
    }
}

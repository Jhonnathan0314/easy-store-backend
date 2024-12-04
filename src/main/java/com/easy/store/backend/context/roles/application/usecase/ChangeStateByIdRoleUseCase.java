package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdRoleUseCase {

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role changeStateById(Long id) throws NonExistenceException {
        Optional<Role> optRole = roleRepository.findById(id);
        if(optRole.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        Role role = optRole.get();
        role.setState(role.getState().equals("active") ? "inactive" : "active");
        role = roleRepository.update(role);
        return role;
    }

}

package com.sophie.store.backend.context.roles.application.usecase;

import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.domain.port.RoleRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExisteceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdRoleUseCase {

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role changeStateById(Long id) throws NonExisteceException {
        Optional<Role> optRole = roleRepository.findById(id);
        if(optRole.isEmpty()) throw new NonExisteceException(errorMessages.NON_EXISTENT_DATA);
        Role role = optRole.get();
        role.setState(role.getState().equals("active") ? "inactive" : "active");
        role = roleRepository.update(role);
        return role;
    }

}

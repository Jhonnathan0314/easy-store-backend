package com.sophie.store.backend.context.roles.application.usecase;

import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.domain.port.RoleRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRoleUseCase {

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role create(Role role) throws DuplicatedException, InvalidBodyException {
        if(!role.isValid(role)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        if(roleRepository.findByName(role.getName()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);
        return roleRepository.create(role);
    }

}

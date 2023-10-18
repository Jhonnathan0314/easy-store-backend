package com.sophie.store.backend.context.roles.application.usecase;

import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.domain.port.RoleRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindByIdRoleUseCase {

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role findById(Long id) throws NoResultsException {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if(optionalRole.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return optionalRole.get();
    }

}

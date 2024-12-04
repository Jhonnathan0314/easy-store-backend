package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateRoleUseCase {

    private final ErrorMessages errorMessages = new ErrorMessages();

    private final RoleRepository roleRepository;

    public Role update(Role role) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {
        if(role.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(!role.isValid(role)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        Optional<Role> optRole = roleRepository.findById(role.getId());
        if(optRole.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        Role roleDb = optRole.get();
        if(roleDb.getName().equals(role.getName())) throw new NoChangesException(errorMessages.NO_CHANGES);

        role.setState(roleDb.getState());
        role.setCreationDate(roleDb.getCreationDate());
        return roleRepository.update(role);
    }

}

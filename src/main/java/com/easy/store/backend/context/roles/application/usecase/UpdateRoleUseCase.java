package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateRoleUseCase {

    private final RoleRepository roleRepository;

    public Role update(Role role) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        log.info("ACCION UDPATE ROLE -> Inicia el proceso");

        if(role.getId() == null) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        log.info("ACCION UDPATE ROLE -> Validé id");

        if(!role.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION UDPATE ROLE -> Validé cuerpo de la petición");

        Optional<Role> optRole = roleRepository.findById(role.getId());
        if(optRole.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION UDPATE ROLE -> Validé existencia del rol");

        Role roleDb = optRole.get();
        if(roleDb.getName().equals(role.getName())) throw new NoChangesException(ErrorMessages.NO_CHANGES);
        log.info("ACCION UDPATE ROLE -> Validé que hayan cambios a aplicar");

        role.setState(roleDb.getState());
        role.setCreationDate(roleDb.getCreationDate());

        log.info("ACCION UDPATE ROLE -> Actualizando rol");

        return roleRepository.update(role);
    }

}

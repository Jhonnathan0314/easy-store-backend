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
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdateRoleUseCase {

    private final Logger logger = Logger.getLogger(UpdateRoleUseCase.class.getName());

    private final RoleRepository roleRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Role update(Role role) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        logger.info("ACCION UDPATE ROLE -> Inicia el proceso");

        if(role.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UDPATE ROLE -> Validé id");

        if(!role.isValid(role)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION UDPATE ROLE -> Validé cuerpo de la petición");

        Optional<Role> optRole = roleRepository.findById(role.getId());
        if(optRole.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        logger.info("ACCION UDPATE ROLE -> Validé existencia del rol");

        Role roleDb = optRole.get();
        if(roleDb.getName().equals(role.getName())) throw new NoChangesException(errorMessages.NO_CHANGES);
        logger.info("ACCION UDPATE ROLE -> Validé que hayan cambios a aplicar");

        role.setState(roleDb.getState());
        role.setCreationDate(roleDb.getCreationDate());

        logger.info("ACCION UDPATE ROLE -> Actualizando rol");

        return roleRepository.update(role);
    }

}

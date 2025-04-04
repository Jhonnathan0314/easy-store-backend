package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DeleteByIdRoleUseCase {

    private final Logger logger = Logger.getLogger(DeleteByIdRoleUseCase.class.getName());

    private final RoleRepository roleRepository;

    public void deleteById(Long id) throws NonExistenceException {

        logger.info("ACCION DELETEBYID ROLE -> Iniciando proceso con id: " + id);

        Optional<Role> role = roleRepository.findById(id);
        if(role.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION DELETEBYID ROLE -> Rol encontrado con éxito");

        logger.info("ACCION DELETEBYID ROLE -> Eliminando rol");

        roleRepository.deleteById(id);
    }

}

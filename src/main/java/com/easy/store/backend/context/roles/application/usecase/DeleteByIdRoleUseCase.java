package com.easy.store.backend.context.roles.application.usecase;

import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteByIdRoleUseCase {

    private final RoleRepository roleRepository;

    public void deleteById(Long id) throws NonExistenceException {

        log.info("ACCION DELETEBYID ROLE -> Iniciando proceso con id: {}", id);

        Optional<Role> role = roleRepository.findById(id);
        if(role.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID ROLE -> Rol encontrado con éxito");

        log.info("ACCION DELETEBYID ROLE -> Eliminando rol");

        roleRepository.deleteById(id);
    }

}

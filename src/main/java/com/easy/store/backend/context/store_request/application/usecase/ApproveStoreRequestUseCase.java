package com.easy.store.backend.context.store_request.application.usecase;

import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.domain.port.AccountRepository;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.port.AccountHasUserRepository;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.roles.domain.port.RoleRepository;
import com.easy.store.backend.context.store_request.domain.model.StoreRequest;
import com.easy.store.backend.context.store_request.domain.port.StoreRequestRepository;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidActionException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApproveStoreRequestUseCase {

    private static final String OWNER_ROLE = "owner";

    private final StoreRequestRepository storeRequestRepository;
    private final AccountRepository accountRepository;
    private final AccountHasUserRepository accountHasUserRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public StoreRequest approve(Long id, Long adminId) throws NoResultsException, InvalidActionException {

        log.info("ACCION APPROVE STORE_REQUEST -> Iniciando proceso con id: {}", id);

        Optional<StoreRequest> optStoreRequest = storeRequestRepository.findById(id);
        if(optStoreRequest.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION APPROVE STORE_REQUEST -> Solicitud encontrada con éxito");

        StoreRequest storeRequest = optStoreRequest.get();
        if(!storeRequest.isPending()) throw new InvalidActionException(ErrorMessages.STORE_REQUEST_NOT_PENDING);
        log.info("ACCION APPROVE STORE_REQUEST -> Validé que la solicitud está pendiente");

        Long userId = storeRequest.getUser().getId();

        List<AccountHasUser> accountsHasUser = accountHasUserRepository.findByUserId(userId);
        if(accountsHasUser.isEmpty()) throw new NoResultsException(ErrorMessages.NO_ACCOUNT_RESULTS);
        Long accountId = accountsHasUser.get(0).getAccountId().getId();

        Optional<Account> optAccount = accountRepository.findById(accountId);
        if(optAccount.isEmpty()) throw new NoResultsException(ErrorMessages.NO_ACCOUNT_RESULTS);
        log.info("ACCION APPROVE STORE_REQUEST -> Cuenta del usuario encontrada con éxito");

        Account account = optAccount.get();
        account.setName(storeRequest.getStoreName());
        account.setDescription(storeRequest.getStoreDescription());
        accountRepository.update(account);
        log.info("ACCION APPROVE STORE_REQUEST -> Cuenta actualizada con nombre y descripción de la tienda");

        Optional<Role> optOwnerRole = roleRepository.findByName(OWNER_ROLE);
        if(optOwnerRole.isEmpty()) throw new NoResultsException(ErrorMessages.NO_ROLE_RESULTS);
        log.info("ACCION APPROVE STORE_REQUEST -> Rol owner encontrado con éxito");

        Optional<User> optUser = userRepository.findById(userId);
        if(optUser.isEmpty()) throw new NoResultsException(ErrorMessages.NO_USER_RESULTS);

        User user = optUser.get();
        user.setRole(optOwnerRole.get());
        userRepository.update(user);
        log.info("ACCION APPROVE STORE_REQUEST -> Rol del usuario actualizado a owner");

        storeRequest.setStatus(StoreRequest.STATUS_APPROVED);
        storeRequest.setReviewedBy(adminId);

        log.info("ACCION APPROVE STORE_REQUEST -> Actualizando solicitud a approved");

        return storeRequestRepository.update(storeRequest);
    }

}

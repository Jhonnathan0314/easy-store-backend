package com.easy.store.backend.security.service;

import com.easy.store.backend.context.account.application.usecase.CreateAccountUseCase;
import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account_has_user.application.usecase.CreateAccountHasUserUseCase;
import com.easy.store.backend.context.account_has_user.application.usecase.FindByUserIdAccountHasUserUseCase;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.codes.application.usecase.DeleteByUserIdCodeUseCase;
import com.easy.store.backend.context.codes.application.usecase.FindByUserIdCodeUseCase;
import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.roles.domain.model.Role;
import com.easy.store.backend.context.user.application.usecase.CreateUserUseCase;
import com.easy.store.backend.context.user.application.usecase.FindByUsernameUserUseCase;
import com.easy.store.backend.context.user.application.usecase.UpdateUserUseCase;
import com.easy.store.backend.security.jwt.JwtService;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.security.models.AuthResponse;
import com.easy.store.backend.security.models.LoginRequest;
import com.easy.store.backend.security.models.ResetPasswordRequest;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final FindByUsernameUserUseCase findByUsernameUserUseCase;
    private final FindByUserIdAccountHasUserUseCase findByUserIdAccountHasUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final CreateAccountHasUserUseCase createAccountHasUserUseCase;
    private final FindByUserIdCodeUseCase findByUserIdCodeUseCase;
    private final DeleteByUserIdCodeUseCase deleteByUserIdCodeUseCase;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${easy.store.ghost.user}")
    private final String ghostUsername;

    @Value("${easy.store.ghost.password}")
    private final String ghostPassword;

    public AuthResponse login(LoginRequest request) throws NoResultsException, InvalidBodyException {

        log.info("ACCION LOGIN -> request: {}", request.getUsername());
        if(request.getUsername().equals(ghostUsername)) {
            log.info("ACCION LOGIN -> Usuario ghost, actualizo password");
            request.setPassword(ghostPassword);
        }

        log.info("ACCION LOGIN -> Validando body");
        if(!request.isValidRequest(request)) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        log.info("ACCION LOGIN -> Validando usuario por username: {}", request.getUsername());
        Optional<User> userDb = findByUsernameUserUseCase.findByUsername(request.getUsername());
        if(userDb.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        log.info("ACCION LOGIN -> Validando account has user");
        List<AccountHasUser> hasUserDb = findByUserIdAccountHasUserUseCase.findByUserId(userDb.get().getId());

        if(hasUserDb.isEmpty()) throw new NoResultsException(ErrorMessages.NON_EXISTENT_DATA);

        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("user_role", userDb.get().getRole().getName());
        extraClaims.put("user_id", userDb.get().getId().toString());
        extraClaims.put("account_id", hasUserDb.get(0).getAccountId().getId().toString());

        String token = jwtService.generateToken(userDb.get(), extraClaims);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(User request) throws InvalidBodyException, DuplicatedException, NonExistenceException, NoResultsException, NoIdReceivedException, NoChangesException {

        log.info("ACCION REGISTER -> request: {}", request.getUsername());
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .lastName(request.getLastName())
                .role(Role.builder()
                        .id(1L)
                        .name("client")
                        .build()
                )
                .build();

        log.info("ACCION REGISTER -> creando usuario en base de datos");
        user = createUserUseCase.create(user);

        log.info("ACCION REGISTER -> creando cuenta en base de datos");
        Account account = createAccountUseCase.create(Account.builder()
                .name("Nombre por defecto")
                .description("Descripción por defecto")
                .build()
        );

        log.info("ACCION REGISTER -> creando account has user en base de datos");
        createAccountHasUserUseCase.create(AccountHasUser.builder()
                .id(AccountHasUserId.builder()
                        .accountId(account.getId())
                        .userId(user.getId())
                        .build()
                )
                .userId(user)
                .accountId(account)
                .build()
        );

        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("user_role", user.getRole().getName());
        extraClaims.put("user_id", user.getId().toString());
        extraClaims.put("account_id", account.getId().toString());

        return AuthResponse.builder()
                .token(jwtService.generateToken(user, extraClaims))
                .build();
    }

    public User resetPassword(ResetPasswordRequest request) throws InvalidBodyException, NoResultsException,
            NoIdReceivedException, NoChangesException, NonExistenceException {
        if(!request.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);

        User userDb = findByUsernameUserUseCase.findByUsername(request.getUsername()).orElse(null);
        if (userDb == null) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        Code codeDb = findByUserIdCodeUseCase.findByUserId(userDb.getId());
        if(codeDb == null) throw new NonExistenceException(ErrorMessages.NO_VALID_CODE);
        if(!Objects.equals(codeDb.getCode(), request.getCode())) throw new NonExistenceException(ErrorMessages.NO_VALID_CODE);

        deleteByUserIdCodeUseCase.deleteByUserId(userDb.getId());

        userDb.setPassword(request.getPassword());
        return updateUserUseCase.update(userDb);
    }

}

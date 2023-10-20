package com.sophie.store.backend.security.service;

import com.sophie.store.backend.context.user.application.usecase.CreateUserUseCase;
import com.sophie.store.backend.context.user.application.usecase.FindByUsernameUserUseCase;
import com.sophie.store.backend.security.jwt.JwtService;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.security.models.AuthResponse;
import com.sophie.store.backend.security.models.LoginRequest;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final FindByUsernameUserUseCase findByUsernameUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) throws NoResultsException, InvalidBodyException {

        if(!request.isValidRequest(request)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        Optional<User> userDb = findByUsernameUserUseCase.findByUsername(request.getUsername());
        if(userDb.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("user_role", userDb.get().getRole().getName());

        String token = jwtService.getToken(userDb.get(), extraClaims);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(User request) throws InvalidBodyException, DuplicatedException {
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .lastName(request.getLastName())
                .build();

        user = createUserUseCase.create(user);

        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("user_role", user.getRole().getName());

        return AuthResponse.builder()
                .token(jwtService.getToken(user, extraClaims))
                .build();
    }

}

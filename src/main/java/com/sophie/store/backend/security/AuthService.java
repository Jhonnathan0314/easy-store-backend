package com.sophie.store.backend.security;

import com.sophie.store.backend.context.user.application.usecase.CreateUserUseCase;
import com.sophie.store.backend.context.user.application.usecase.FindByUsernameUserUseCase;
import com.sophie.store.backend.security.jwt.JwtService;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.domain.port.UserRepository;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final FindByUsernameUserUseCase findByUsernameUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User userDb = findByUsernameUserUseCase.findByUsername(request.getUsername()).orElseThrow();

        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("user_role", userDb.getRole().getName());

        String token = jwtService.getToken(userDb, extraClaims);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) throws InvalidBodyException, DuplicatedException {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .build();

        user = createUserUseCase.create(user);

        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("user_role", user.getRole().getName());

        return AuthResponse.builder()
                .token(jwtService.getToken(user, extraClaims))
                .build();
    }

}

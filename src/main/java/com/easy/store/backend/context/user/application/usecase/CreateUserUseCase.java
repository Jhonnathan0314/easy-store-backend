package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();
    private final PasswordEncoder passwordEncoder;

    public User create(User user) throws DuplicatedException, InvalidBodyException {

        if(user.getPassword() == null) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(!user.isValid(user)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        if(userRepository.findByUsername(user.getUsername()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        return userRepository.create(user);
    }

}

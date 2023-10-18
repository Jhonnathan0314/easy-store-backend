package com.sophie.store.backend.context.user.application.usecase;

import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.domain.port.UserRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.DuplicatedException;
import com.sophie.store.backend.utils.exceptions.InvalidBodyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public User create(User user) throws DuplicatedException, InvalidBodyException {

        if(!user.isValid(user)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        if(userRepository.findByUsername(user.getUsername()).isPresent()) throw new DuplicatedException(errorMessages.DUPLICATED);

        return userRepository.create(user);
    }

}

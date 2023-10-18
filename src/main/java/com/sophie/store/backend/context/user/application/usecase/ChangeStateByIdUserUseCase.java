package com.sophie.store.backend.context.user.application.usecase;

import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.domain.port.UserRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NonExisteceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeStateByIdUserUseCase {

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public User changeStateById(Long id) throws NonExisteceException {
        Optional<User> optRole = userRepository.findById(id);
        if(optRole.isEmpty()) throw new NonExisteceException(errorMessages.NON_EXISTENT_DATA);
        User user = optRole.get();
        user.setState(user.getState().equals("active") ? "inactive" : "active");
        user = userRepository.update(user);
        return user;
    }
}

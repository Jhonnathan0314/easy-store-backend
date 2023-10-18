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
public class DeleteByIdUserUseCase {

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public void deleteById(Long id) throws NonExisteceException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new NonExisteceException(errorMessages.NON_EXISTENT_DATA);
        userRepository.deleteById(id);
    }

}

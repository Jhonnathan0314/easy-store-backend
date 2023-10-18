package com.sophie.store.backend.context.user.application.usecase;

import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.domain.port.UserRepository;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindAllUserUseCase {

    private final UserRepository userRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public List<User> findAll() throws NoResultsException {
        List<User> users = userRepository.findAll();
        if(users == null || users.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);
        return users;
    }

}

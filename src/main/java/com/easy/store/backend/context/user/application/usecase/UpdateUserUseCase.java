package com.easy.store.backend.context.user.application.usecase;

import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoChangesException;
import com.easy.store.backend.utils.exceptions.NoIdReceivedException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final ErrorMessages errorMessages = new ErrorMessages();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User update(User user) throws NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        if(user.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);

        if(!user.isValid(user)) throw new InvalidBodyException(errorMessages.INVALID_BODY);

        Optional<User> optUser = userRepository.findById(user.getId());
        if(optUser.isEmpty()) throw new NoResultsException(errorMessages.NO_RESULTS);

        User userDb = optUser.get();
        if(userDb.getName().equals(user.getName())) throw new NoChangesException(errorMessages.NO_CHANGES);

        if(user.getRole() == null) user.setRole(userDb.getRole());

        user.setState(userDb.getState());

        if(user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setPassword(userDb.getPassword());

        return userRepository.update(user);
    }

}

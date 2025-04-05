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
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final Logger logger = Logger.getLogger(UpdateUserUseCase.class.getName());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User update(User user) throws
            NoIdReceivedException, NoResultsException, NoChangesException, InvalidBodyException {

        logger.info("ACCION UDPATE USER -> Inicia el proceso con body: " + user.toString());

        if(user.getId() == null) throw new NoIdReceivedException(ErrorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UDPATE USER -> Validé id");

        if(!user.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        logger.info("ACCION UDPATE USER -> Validé cuerpo de la petición");

        Optional<User> optUser = userRepository.findById(user.getId());
        if(optUser.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        logger.info("ACCION UDPATE USER -> Validé existencia de la subcategoria");

        User userDb = optUser.get();
        if(!areDifferences(user, userDb)) throw new NoChangesException(ErrorMessages.NO_CHANGES);
        logger.info("ACCION UDPATE USER -> Validé que hayan cambios a aplicar");

        if(user.getRole() == null) user.setRole(userDb.getRole());

        user.setState(userDb.getState());

        if(user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userDb.getPassword());
        }

        logger.info("ACCION UDPATE USER -> Actualizando subcategoria");

        return userRepository.update(user);
    }

    private boolean areDifferences(User user1, User user2) {
        return !user1.getName().equals(user2.getName()) ||
                !user1.getLastName().equals(user2.getLastName()) ||
                !user1.getUsername().equals(user2.getUsername()) ||
                user1.getPassword() != null;
    }

}

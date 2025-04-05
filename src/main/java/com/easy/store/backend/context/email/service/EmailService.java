package com.easy.store.backend.context.email.service;

import com.easy.store.backend.context.codes.application.usecase.CreateCodeUseCase;
import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.user.application.usecase.FindByUsernameUserUseCase;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final FindByUsernameUserUseCase findByUsernameUserUseCase;
    private final CreateCodeUseCase createCodeUseCase;

    public void sendEmail(String username) throws Exception {

        if(username == null || username.isEmpty()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);

        User userDb = findByUsernameUserUseCase.findByUsername(username).orElse(null);
        if(userDb == null) throw new NoResultsException(ErrorMessages.NO_RESULTS);

        Code code = createCodeUseCase.create(Code.builder()
                .userId(userDb.getId())
                .code(ThreadLocalRandom.current().nextLong(1000, 10000))
                .action("forgot-password")
                .build());

        if(code == null) throw new Exception(ErrorMessages.GENERIC_ERROR);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("easy.pc.mail.info@gmail.com");
        message.setTo(username);
        message.setSubject("Restablecer contraseña");
        message.setText("Este es el código para reestablecer tu contraseña en Easy Store: " + code.getCode() +
                "\n\nRecuerde que expira en 10 minutos.");
        mailSender.send(message);

    }

}

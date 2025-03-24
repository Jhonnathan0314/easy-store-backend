package com.easy.store.backend.context.email.service;

import com.easy.store.backend.context.codes.application.usecase.CreateCodeUseCase;
import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.context.user.application.usecase.FindByIdUserUseCase;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.utils.constants.ErrorMessages;
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
    private final FindByIdUserUseCase findByIdUserUseCase;
    private final CreateCodeUseCase createCodeUseCase;

    private final ErrorMessages errorMessages = new ErrorMessages();

    public void sendEmail(Long userId) throws Exception {

        User userDb = findByIdUserUseCase.findById(userId);
        if(userDb == null) throw new NoResultsException(errorMessages.NO_RESULTS);

        Code code = createCodeUseCase.create(Code.builder()
                .userId(userId)
                .code(ThreadLocalRandom.current().nextLong(1000, 10000))
                .action("forgot-password")
                .build());

        if(code == null) throw new Exception(errorMessages.GENERIC_ERROR);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("easy.pc.mail.info@gmail.com");
        message.setTo(userDb.getUsername());
        message.setSubject("Restablecer contraseña");
        message.setText("Este es el código para reestablecer tu contraseña en Easy Store: " + code.getCode() +
                "\n\nRecuerde que expira en 10 minutos.");
        mailSender.send(message);

    }

}

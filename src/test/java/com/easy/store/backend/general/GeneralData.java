package com.easy.store.backend.general;

import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.messages.ErrorMessage;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GeneralData {

    //Required
    private final ErrorMessages errorMessages = new ErrorMessages();

    //Response errors
    private ErrorMessage errorInvalidBody = ErrorMessage.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .title(HttpStatus.BAD_REQUEST.name())
            .detail(errorMessages.INVALID_BODY)
            .build();

    private ErrorMessage errorNoResults = ErrorMessage.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .title(HttpStatus.NOT_FOUND.name())
            .detail(errorMessages.NO_RESULTS)
            .build();

    private ErrorMessage errorDuplicated = ErrorMessage.builder()
            .code(HttpStatus.CONFLICT.value())
            .title(HttpStatus.CONFLICT.name())
            .detail(errorMessages.DUPLICATED)
            .build();

    private ErrorMessage errorNoIdReceived = ErrorMessage.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .title(HttpStatus.BAD_REQUEST.name())
            .detail(errorMessages.NO_ID_RECEIVED)
            .build();

    private ErrorMessage errorNoChanges = ErrorMessage.builder()
            .code(HttpStatus.NOT_ACCEPTABLE.value())
            .title(HttpStatus.NOT_ACCEPTABLE.name())
            .detail(errorMessages.NO_CHANGES)
            .build();

    private ErrorMessage errorNonExistence = ErrorMessage.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .title(HttpStatus.NOT_FOUND.name())
            .detail(errorMessages.NON_EXISTENT_DATA)
            .build();

    //Constructor
    public GeneralData() { }

}

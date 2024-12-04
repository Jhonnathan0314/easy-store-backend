package com.easy.store.backend.utils.data;

import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.messages.ErrorMessage;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UtilsData {

    //Required
    private final ErrorMessages errorMessages = new ErrorMessages();

    //Error messages
    private ErrorMessage errorMessageDuplicatedException = ErrorMessage.builder()
            .code(HttpStatus.CONFLICT.value())
            .title(HttpStatus.CONFLICT.name())
            .detail(errorMessages.DUPLICATED)
            .build();

    private ErrorMessage errorMessageInvalidBodyException = ErrorMessage.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .title(HttpStatus.BAD_REQUEST.name())
            .detail(errorMessages.INVALID_BODY)
            .build();

    private ErrorMessage errorMessageNoIdReceivedException = ErrorMessage.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .title(HttpStatus.BAD_REQUEST.name())
            .detail(errorMessages.NO_ID_RECEIVED)
            .build();

    private ErrorMessage errorMessageNoChangesException = ErrorMessage.builder()
            .code(HttpStatus.NOT_ACCEPTABLE.value())
            .title(HttpStatus.NOT_ACCEPTABLE.name())
            .detail(errorMessages.NO_CHANGES)
            .build();

    private ErrorMessage errorMessageNonExistenceException = ErrorMessage.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .title(HttpStatus.NOT_FOUND.name())
            .detail(errorMessages.NON_EXISTENT_DATA)
            .build();

    private ErrorMessage errorMessageNoResultsException = ErrorMessage.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .title(HttpStatus.NOT_FOUND.name())
            .detail(errorMessages.NO_RESULTS)
            .build();

    private ErrorMessage errorMessageOtherException = ErrorMessage.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .title(HttpStatus.INTERNAL_SERVER_ERROR.name())
            .detail(null)
            .build();


}

package com.sophie.store.backend.utils.http;

import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.data.UtilsData;
import com.sophie.store.backend.utils.exceptions.*;
import com.sophie.store.backend.utils.messages.ErrorMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpUtilsTest {

    private static HttpUtils httpUtils;
    private static ErrorMessages errorMessages;
    private static UtilsData utilsData;

    @BeforeAll
    static void setUp() {
        httpUtils = new HttpUtils();
        errorMessages = new ErrorMessages();
        utilsData = new UtilsData();
    }

    //Determine HttpStatus
    @Test
    @Order(0)
    void determineHttpStatusWithDuplicatedException() {
        DuplicatedException exception = new DuplicatedException(errorMessages.DUPLICATED);
        HttpStatus status = httpUtils.determineHttpStatus(exception);
        assertEquals(HttpStatus.CONFLICT, status);
    }

    @Test
    @Order(1)
    void determineHttpStatusWithInvalidBodyException() {
        InvalidBodyException exception = new InvalidBodyException(errorMessages.INVALID_BODY);
        HttpStatus status = httpUtils.determineHttpStatus(exception);
        assertEquals(HttpStatus.BAD_REQUEST, status);
    }

    @Test
    @Order(1)
    void determineHttpStatusWithNoIdReceivedException() {
        NoIdReceivedException exception = new NoIdReceivedException(errorMessages.INVALID_BODY);
        HttpStatus status = httpUtils.determineHttpStatus(exception);
        assertEquals(HttpStatus.BAD_REQUEST, status);
    }

    @Test
    @Order(2)
    void determineHttpStatusWithNoChangesException() {
        NoChangesException exception = new NoChangesException(errorMessages.INVALID_BODY);
        HttpStatus status = httpUtils.determineHttpStatus(exception);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, status);
    }

    @Test
    @Order(3)
    void determineHttpStatusWithNonExistenceException() {
        NonExistenceException exception = new NonExistenceException(errorMessages.INVALID_BODY);
        HttpStatus status = httpUtils.determineHttpStatus(exception);
        assertEquals(HttpStatus.NOT_FOUND, status);
    }

    @Test
    @Order(4)
    void determineHttpStatusWithNoResultsException() {
        NoResultsException exception = new NoResultsException(errorMessages.INVALID_BODY);
        HttpStatus status = httpUtils.determineHttpStatus(exception);
        assertEquals(HttpStatus.NOT_FOUND, status);
    }

    @Test
    @Order(5)
    void determineHttpStatusWithOtherException() {
        Exception exception = new Exception();
        HttpStatus status = httpUtils.determineHttpStatus(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status);
    }

    //Determine ErrorMessage
    @Test
    @Order(0)
    void determineErrorMessageWithDuplicatedException() {
        DuplicatedException exception = new DuplicatedException(errorMessages.DUPLICATED);
        ErrorMessage errorMessage = httpUtils.determineErrorMessage(exception);
        assertEquals(utilsData.getErrorMessageDuplicatedException(), errorMessage);
    }

    @Test
    @Order(1)
    void determineErrorMessageWithInvalidBodyException() {
        InvalidBodyException exception = new InvalidBodyException(errorMessages.INVALID_BODY);
        ErrorMessage errorMessage = httpUtils.determineErrorMessage(exception);
        assertEquals(utilsData.getErrorMessageInvalidBodyException(), errorMessage);
    }

    @Test
    @Order(1)
    void determineErrorMessageWithNoIdReceivedException() {
        NoIdReceivedException exception = new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        ErrorMessage errorMessage = httpUtils.determineErrorMessage(exception);
        assertEquals(utilsData.getErrorMessageNoIdReceivedException(), errorMessage);
    }

    @Test
    @Order(2)
    void determineErrorMessageWithNoChangesException() {
        NoChangesException exception = new NoChangesException(errorMessages.NO_CHANGES);
        ErrorMessage errorMessage = httpUtils.determineErrorMessage(exception);
        assertEquals(utilsData.getErrorMessageNoChangesException(), errorMessage);
    }

    @Test
    @Order(3)
    void determineErrorMessageWithNonExistenceException() {
        NonExistenceException exception = new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        ErrorMessage errorMessage = httpUtils.determineErrorMessage(exception);
        assertEquals(utilsData.getErrorMessageNonExistenceException(), errorMessage);
    }

    @Test
    @Order(4)
    void determineErrorMessageWithNoResultsException() {
        NoResultsException exception = new NoResultsException(errorMessages.NO_RESULTS);
        ErrorMessage errorMessage = httpUtils.determineErrorMessage(exception);
        assertEquals(utilsData.getErrorMessageNoResultsException(), errorMessage);
    }

    @Test
    @Order(5)
    void determineErrorMessageWithOtherException() {
        Exception exception = new Exception();
        ErrorMessage errorMessage = httpUtils.determineErrorMessage(exception);
        assertEquals(utilsData.getErrorMessageOtherException(), errorMessage);
    }
}
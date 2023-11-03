package com.sophie.store.backend.security.data;

import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.security.models.AuthResponse;
import com.sophie.store.backend.security.models.LoginRequest;
import com.sophie.store.backend.utils.constants.ErrorMessages;
import com.sophie.store.backend.utils.messages.ApiResponse;
import com.sophie.store.backend.utils.messages.ErrorMessage;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SecurityData {

    //Required
    private final ErrorMessages errorMessages = new ErrorMessages();

    //Request bodies
    private LoginRequest loginRequest = LoginRequest.builder()
            .username("test@test.com")
            .password("12345")
            .build();;

    private LoginRequest badLoginRequest = LoginRequest.builder().build();

    private User registerRequest = User.builder()
            .username("test@test.com")
                .password("12345")
                .role(Role.builder()
                        .id(1L)
                        .name("client")
                        .build()
                )
                        .name("test")
                .lastName("test")
                .build();

    private User badRegisterRequest = User.builder()
            .username("test@test.com")
            .role(Role.builder()
                    .id(1L)
                    .name("client")
                    .build()
            )
            .build();

    //Response success
    private AuthResponse tokenResponse = AuthResponse.builder()
            .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
            .build();

    private ApiResponse<AuthResponse> apiResponse;

    //Response errors
    private ErrorMessage errorInvalidBody = ErrorMessage.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .title(HttpStatus.BAD_REQUEST.name())
            .detail(errorMessages.INVALID_BODY)
            .build();

    private ApiResponse<AuthResponse> errorResponseInvalidBody;

    private ErrorMessage errorNoResults = ErrorMessage.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .title(HttpStatus.NOT_FOUND.name())
            .detail(errorMessages.NO_RESULTS)
            .build();

    private ApiResponse<AuthResponse> errorResponseNoResults;

    private ErrorMessage errorDuplicated = ErrorMessage.builder()
            .code(HttpStatus.CONFLICT.value())
            .title(HttpStatus.CONFLICT.name())
            .detail(errorMessages.DUPLICATED)
            .build();

    private ApiResponse<AuthResponse> errorResponseDuplicated;

    //Constructor
    public SecurityData() {
        apiResponse = new ApiResponse<>();
        apiResponse.setData(tokenResponse);

        errorResponseInvalidBody = new ApiResponse<>();
        errorResponseInvalidBody.setData(null);
        errorResponseInvalidBody.setError(errorInvalidBody);

        errorResponseNoResults = new ApiResponse<>();
        errorResponseNoResults.setData(null);
        errorResponseNoResults.setError(errorNoResults);

        errorResponseDuplicated = new ApiResponse<>();
        errorResponseDuplicated.setData(null);
        errorResponseDuplicated.setError(errorDuplicated);
    }

}

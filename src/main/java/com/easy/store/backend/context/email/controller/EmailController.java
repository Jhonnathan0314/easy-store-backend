package com.easy.store.backend.context.email.controller;

import com.easy.store.backend.context.email.service.EmailService;
import com.easy.store.backend.utils.http.HttpUtils;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EmailController {

    private final EmailService emailService;

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping("/forgot-password/user/{id}")
    public ResponseEntity<ApiResponse<Boolean>> sendConfirmationCode(@PathVariable Long id) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            emailService.sendEmail(id);
            response.setData(Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}

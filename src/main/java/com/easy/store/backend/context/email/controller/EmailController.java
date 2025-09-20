package com.easy.store.backend.context.email.controller;

import com.easy.store.backend.context.email.service.EmailService;
import com.easy.store.backend.context.user.application.dto.UserDTO;
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

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Boolean>> sendConfirmationCode(@RequestBody UserDTO user) throws Exception {
        ApiResponse<Boolean> response = new ApiResponse<>();
        emailService.sendEmail(user.getUsername());
        response.setData(Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}

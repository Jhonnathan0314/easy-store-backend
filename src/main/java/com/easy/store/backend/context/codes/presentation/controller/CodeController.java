package com.easy.store.backend.context.codes.presentation.controller;

import com.easy.store.backend.context.codes.application.usecase.*;
import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import com.easy.store.backend.utils.http.HttpUtils;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CodeController {

    private final FindAllCodeUseCase findAllCodeUseCase;
    private final FindByUserIdCodeUseCase findByUserIdCodeUseCase;
    private final CreateCodeUseCase createCodeUseCase;
    private final DeleteByUserIdCodeUseCase deleteByUserIdCodeUseCase;
    private final DeleteExpiredCodeUseCase deleteExpiredCodeUseCase;

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Code>>> findAll() {
        ApiResponse<List<Code>> response = new ApiResponse<>();
        try {
            List<Code> codes = findAllCodeUseCase.findAll();
            response.setData(codes);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<Code>> findByUserId(@PathVariable("id") Long id) {
        ApiResponse<Code> response = new ApiResponse<>();
        try {
            Code code = findByUserIdCodeUseCase.findByUserId(id);
            response.setData(code);
            return ResponseEntity.ok(response);
        } catch (NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse<Boolean>> create(@RequestBody Code code) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            createCodeUseCase.create(code);
            response.setData(Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (InvalidBodyException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse<Boolean>> create(@PathVariable("id") Long id) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            deleteByUserIdCodeUseCase.deleteByUserId(id);
            response.setData(Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (NoResultsException | NonExistenceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @Scheduled(cron = "0 59 23 * * *")
//    @Scheduled(cron = "0 */15 * * * *") se comenta temporalmente para disminuir consumo ya que la app no tiene un numero elevado de usuarios.
    private void deleteExpiredCodes() {
        try {
            List<Code> codes = findAllCodeUseCase.findAll();
            if(!codes.isEmpty()) deleteExpiredCodeUseCase.deleteExpired(codes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

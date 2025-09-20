package com.easy.store.backend.context.codes.presentation.controller;

import com.easy.store.backend.context.codes.application.usecase.*;
import com.easy.store.backend.context.codes.domain.model.Code;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
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

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Code>>> findAll() throws NoResultsException {
        ApiResponse<List<Code>> response = new ApiResponse<>();
        List<Code> codes = findAllCodeUseCase.findAll();
        response.setData(codes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<Code>> findByUserId(@PathVariable("id") Long id) throws NonExistenceException {
        ApiResponse<Code> response = new ApiResponse<>();
        Code code = findByUserIdCodeUseCase.findByUserId(id);
        response.setData(code);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse<Boolean>> create(@RequestBody Code code) throws InvalidBodyException {
        ApiResponse<Boolean> response = new ApiResponse<>();
        createCodeUseCase.create(code);
        response.setData(Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse<Boolean>> create(@PathVariable("id") Long id) throws NoResultsException, NonExistenceException {
        ApiResponse<Boolean> response = new ApiResponse<>();
        deleteByUserIdCodeUseCase.deleteByUserId(id);
        response.setData(Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @Scheduled(cron = "0 59 23 * * *")
//    @Scheduled(cron = "0 */15 * * * *") se comenta temporalmente para disminuir consumo ya que la app no tiene un numero elevado de usuarios.
    private void deleteExpiredCodes() throws NoResultsException {
        List<Code> codes = findAllCodeUseCase.findAll();
        if(!codes.isEmpty()) deleteExpiredCodeUseCase.deleteExpired(codes);
    }

}

package com.easy.store.backend.context.account_has_user.presentation.controller;

import com.easy.store.backend.context.account_has_user.application.dto.AccountHasUserCreateDto;
import com.easy.store.backend.context.account_has_user.application.dto.AccountHasUserResponseDto;
import com.easy.store.backend.context.account_has_user.application.usecase.*;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.account_has_user.infrastructure.mappers.AccountHasUserCreateMapper;
import com.easy.store.backend.context.account_has_user.infrastructure.mappers.AccountHasUserResponseMapper;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/account_has_user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AccountHasUserController {

    private final FindByAccountIdAccountHasUserUseCase findByAccountIdAccountHasUserUseCase;
    private final FindByUserIdAccountHasUserUseCase findByUserIdAccountHasUserUseCase;
    private final FindByIdAccountHasUserUseCase findByIdAccountHasUserUseCase;
    private final FindByStateAccountHasUserUseCase findByStateAccountHasUserUseCase;
    private final CreateAccountHasUserUseCase createAccountHasUserUseCase;
    private final DeleteByIdAccountHasUserUseCase deleteByIdAccountHasUserUseCase;

    private final AccountHasUserCreateMapper accountHasUserCreateMapper = new AccountHasUserCreateMapper();
    private final AccountHasUserResponseMapper accountHasUserResponseMapper = new AccountHasUserResponseMapper();

    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<AccountHasUserResponseDto>>> findByIdAccount(@PathVariable Long id) throws NoResultsException {
        ApiResponse<List<AccountHasUserResponseDto>> response = new ApiResponse<>();
        List<AccountHasUser> users = findByAccountIdAccountHasUserUseCase.findByAccountId(id);
        response.setData(accountHasUserResponseMapper.modelsToDtos(users));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<List<AccountHasUserResponseDto>>> findByIdUser(@PathVariable Long id) throws NoResultsException {
        ApiResponse<List<AccountHasUserResponseDto>> response = new ApiResponse<>();
        List<AccountHasUser> users = findByUserIdAccountHasUserUseCase.findByUserId(id);
        response.setData(accountHasUserResponseMapper.modelsToDtos(users));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{idAccount}/user/{idUser}")
    public ResponseEntity<ApiResponse<AccountHasUserResponseDto>> findById(@PathVariable Long idAccount, @PathVariable Long idUser) throws NoResultsException {
        ApiResponse<AccountHasUserResponseDto> response = new ApiResponse<>();
        AccountHasUserId id = AccountHasUserId.builder()
                .accountId(idAccount)
                .userId(idUser)
                .build();
        AccountHasUser user = findByIdAccountHasUserUseCase.findById(id);
        response.setData(accountHasUserResponseMapper.modelToDto(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<ApiResponse<List<AccountHasUserResponseDto>>> findByState(@PathVariable String state) throws NoResultsException {
        ApiResponse<List<AccountHasUserResponseDto>> response = new ApiResponse<>();
        List<AccountHasUser> model = findByStateAccountHasUserUseCase.findByState(state);
        response.setData(accountHasUserResponseMapper.modelsToDtos(model));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountHasUserResponseDto>> create(@RequestBody AccountHasUserCreateDto dto) throws InvalidBodyException, DuplicatedException, NonExistenceException {
        ApiResponse<AccountHasUserResponseDto> response = new ApiResponse<>();
        AccountHasUser model = createAccountHasUserUseCase.create(accountHasUserCreateMapper.dtoToModel(dto));
        response.setData(accountHasUserResponseMapper.modelToDto(model));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/account/{idAccount}/user/{idUser}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long idAccount, @PathVariable Long idUser) throws NonExistenceException {
        ApiResponse<Object> response = new ApiResponse<>();
        AccountHasUserId id = AccountHasUserId.builder()
                .accountId(idAccount)
                .userId(idUser)
                .build();
        deleteByIdAccountHasUserUseCase.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}

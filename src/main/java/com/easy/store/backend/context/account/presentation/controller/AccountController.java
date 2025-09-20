package com.easy.store.backend.context.account.presentation.controller;

import com.easy.store.backend.context.account.application.dto.AccountCreateDto;
import com.easy.store.backend.context.account.application.dto.AccountDto;
import com.easy.store.backend.context.account.application.dto.AccountUpdateDto;
import com.easy.store.backend.context.account.application.usecase.*;
import com.easy.store.backend.context.account.domain.model.Account;
import com.easy.store.backend.context.account.infrastructure.mappers.AccountCreateMapper;
import com.easy.store.backend.context.account.infrastructure.mappers.AccountMapper;
import com.easy.store.backend.context.account.infrastructure.mappers.AccountUpdateMapper;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AccountController {

    private final FindAllAccountUseCase findAllAccountUseCase;
    private final FindByIdAccountUseCase findByIdAccountUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteByIdAccountUseCase deleteByIdAccountUseCase;

    private final AccountMapper accountMapper = new AccountMapper();
    private final AccountCreateMapper accountCreateMapper = new AccountCreateMapper();
    private final AccountUpdateMapper accountUpdateMapper = new AccountUpdateMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountDto>>> findAll() throws NoResultsException {
        ApiResponse<List<AccountDto>> response = new ApiResponse<>();
        List<Account> accounts = findAllAccountUseCase.findAll();
        response.setData(accountMapper.modelsToDtos(accounts));
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<AccountDto>> findById(@PathVariable Long id) throws NoResultsException {
        ApiResponse<AccountDto> response = new ApiResponse<>();
        Account account = findByIdAccountUseCase.findById(id);
        response.setData(accountMapper.modelToDto(account));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountDto>> create(@RequestBody AccountCreateDto dto) throws InvalidBodyException {
        ApiResponse<AccountDto> response = new ApiResponse<>();
        Account account = createAccountUseCase.create(accountCreateMapper.dtoToModel(dto));
        response.setData(accountMapper.modelToDto(account));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AccountDto>> update(@RequestBody AccountUpdateDto dto) throws NoIdReceivedException, InvalidBodyException, NoChangesException, NonExistenceException {
        ApiResponse<AccountDto> response = new ApiResponse<>();
        Account account = updateAccountUseCase.update(accountUpdateMapper.dtoToModel(dto));
        response.setData(accountMapper.modelToDto(account));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) throws NonExistenceException {
        ApiResponse<Object> response = new ApiResponse<>();
        deleteByIdAccountUseCase.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}

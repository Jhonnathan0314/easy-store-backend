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
import com.easy.store.backend.utils.http.HttpUtils;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AccountController {

    private final Logger logger = Logger.getLogger(AccountController.class.getName());

    private final FindAllAccountUseCase findAllAccountUseCase;
    private final FindByIdAccountUseCase findByIdAccountUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteByIdAccountUseCase deleteByIdAccountUseCase;

    private final AccountMapper accountMapper = new AccountMapper();
    private final AccountCreateMapper accountCreateMapper = new AccountCreateMapper();
    private final AccountUpdateMapper accountUpdateMapper = new AccountUpdateMapper();

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountDto>>> findAll() {
        logger.info("ACCION FINDALL ACCOUNT -> Inicia consulta cuentas");
        ApiResponse<List<AccountDto>> response = new ApiResponse<>();
        try {
            List<Account> accounts = findAllAccountUseCase.findAll();
            response.setData(accountMapper.modelsToDtos(accounts));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            logger.info("ACCION FINDALL ACCOUNT -> No encontre cuentas - NoResultsException");
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<AccountDto>> findById(@PathVariable Long id) {
        logger.info("ACCION FINDBYID ACCOUNT -> Inicia consulta cuenta con id: " + id);
        ApiResponse<AccountDto> response = new ApiResponse<>();
        try {
            Account account = findByIdAccountUseCase.findById(id);
            response.setData(accountMapper.modelToDto(account));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            logger.info("ACCION FINDBYID ACCOUNT -> No encontre cuenta indicada - NoResultsException");
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountDto>> create(@RequestBody AccountCreateDto dto) {
        logger.info("ACCION CREATE ACCOUNT -> Inicia creacion cuenta con body: " + dto.toString());
        ApiResponse<AccountDto> response = new ApiResponse<>();
        try {
            Account account = createAccountUseCase.create(accountCreateMapper.dtoToModel(dto));
            response.setData(accountMapper.modelToDto(account));
            return ResponseEntity.ok(response);
        }catch (InvalidBodyException e) {
            logger.info("ACCION CREATE ACCOUNT -> Ha ocurrido un error al crear cuenta: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<AccountDto>> update(@RequestBody AccountUpdateDto dto) {
        logger.info("ACCION UPDATE ACCOUNT -> Inicia actualizacion cuenta con body: " + dto.toString());
        ApiResponse<AccountDto> response = new ApiResponse<>();
        try {
            Account account = updateAccountUseCase.update(accountUpdateMapper.dtoToModel(dto));
            response.setData(accountMapper.modelToDto(account));
            return ResponseEntity.ok(response);
        }catch (NoIdReceivedException | InvalidBodyException | NonExistenceException | NoChangesException e) {
            logger.info("ACCION UPDATE ACCOUNT -> Ha ocurrido un error al actualizar cuenta: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) {
        logger.info("ACCION DELETEBYID ACCOUNT -> Inicia eliminacion cuenta con id: " + id);
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            deleteByIdAccountUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }catch (NonExistenceException e) {
            logger.info("ACCION DELETEBYID ACCOUNT -> Ha ocurrido un error al eliminar cuenta: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}

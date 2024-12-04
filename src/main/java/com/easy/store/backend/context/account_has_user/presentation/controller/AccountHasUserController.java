package com.easy.store.backend.context.account_has_user.presentation.controller;

import com.easy.store.backend.context.account_has_user.application.dto.AccountHasUserCreateDto;
import com.easy.store.backend.context.account_has_user.application.dto.AccountHasUserDto;
import com.easy.store.backend.context.account_has_user.application.usecase.*;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUser;
import com.easy.store.backend.context.account_has_user.domain.model.AccountHasUserId;
import com.easy.store.backend.context.account_has_user.infrastructure.mappers.AccountHasUserCreateMapper;
import com.easy.store.backend.context.account_has_user.infrastructure.mappers.AccountHasUserMapper;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import com.easy.store.backend.utils.http.HttpUtils;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/account_has_user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AccountHasUserController {

    private final Logger logger = Logger.getLogger(AccountHasUserController.class.getName());

    private final FindByAccountIdAccountHasUserUseCase findByAccountIdAccountHasUserUseCase;
    private final FindByUserIdAccountHasUserUseCase findByUserIdAccountHasUserUseCase;
    private final FindByIdAccountHasUserUseCase findByIdAccountHasUserUseCase;
    private final FindByStateAccountHasUserUseCase findByStateAccountHasUserUseCase;
    private final CreateAccountHasUserUseCase createAccountHasUserUseCase;
    private final DeleteByIdAccountHasUserUseCase deleteByIdAccountHasUserUseCase;

    private final AccountHasUserMapper accountHasUserMapper = new AccountHasUserMapper();
    private final AccountHasUserCreateMapper accountHasUserCreateMapper = new AccountHasUserCreateMapper();

    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping("/account/{id}")
    public ResponseEntity<ApiResponse<List<AccountHasUserDto>>> findByIdAccount(@PathVariable Long id) {
        logger.info("ACCION FINDBYIDACCOUNT ACCOUNT_HAS_ROLE -> Inicia consulta cuenta tiene usuario con id account: " + id);
        ApiResponse<List<AccountHasUserDto>> response = new ApiResponse<>();
        try {
            List<AccountHasUser> users = findByAccountIdAccountHasUserUseCase.findByAccountId(id);
            response.setData(accountHasUserMapper.modelsToDtos(users));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            logger.info("ACCION FINDBYIDACCOUNT ACCOUNT_HAS_ROLE -> No encontre resultados - NoResultsException");
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<List<AccountHasUserDto>>> findByIdUser(@PathVariable Long id) {
        logger.info("ACCION FINDBYIDUSER ACCOUNT_HAS_ROLE -> Inicia consulta cuenta tiene usuario con id user: " + id);
        ApiResponse<List<AccountHasUserDto>> response = new ApiResponse<>();
        try {
            List<AccountHasUser> users = findByUserIdAccountHasUserUseCase.findByUserId(id);
            response.setData(accountHasUserMapper.modelsToDtos(users));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            logger.info("ACCION FINDBYIDUSER ACCOUNT_HAS_ROLE -> No encontre resultados - NoResultsException");
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/account/{idAccount}/user/{idUser}")
    public ResponseEntity<ApiResponse<AccountHasUserDto>> findById(@PathVariable Long idAccount, @PathVariable Long idUser) {
        logger.info("ACCION FINDBYID ACCOUNT_HAS_ROLE -> Inicia consulta cuenta tiene usuario con id cuenta: " + idAccount + " | id usuario: " + idUser);
        ApiResponse<AccountHasUserDto> response = new ApiResponse<>();
        try {
            AccountHasUserId id = AccountHasUserId.builder()
                    .accountId(idAccount)
                    .userId(idUser)
                    .build();
            AccountHasUser user = findByIdAccountHasUserUseCase.findById(id);
            response.setData(accountHasUserMapper.modelToDto(user));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            logger.info("ACCION FINDBYID ACCOUNT_HAS_ROLE -> No encontre resultados - NoResultsException");
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<ApiResponse<List<AccountHasUserDto>>> findByState(@PathVariable String state) {
        logger.info("ACCION FINDBYSTATE ACCOUNT_HAS_ROLE -> Inicia consulta cuenta tiene usuario con estado: " + state);
        ApiResponse<List<AccountHasUserDto>> response = new ApiResponse<>();
        try {
            List<AccountHasUser> model = findByStateAccountHasUserUseCase.findByState(state);
            response.setData(accountHasUserMapper.modelsToDtos(model));
            return ResponseEntity.ok(response);
        }catch (NoResultsException e) {
            logger.info("ACCION FINDBYSTATE ACCOUNT_HAS_ROLE -> No encontre resultados - NoResultsException");
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AccountHasUserDto>> create(@RequestBody AccountHasUserCreateDto dto) {
        logger.info("ACCION CREATE ACCOUNT_HAS_ROLE -> Inicia creacion cuenta tiene usuario con body: " + dto.toString());
        ApiResponse<AccountHasUserDto> response = new ApiResponse<>();
        try {
            AccountHasUser model = createAccountHasUserUseCase.create(accountHasUserCreateMapper.dtoToModel(dto));
            response.setData(accountHasUserMapper.modelToDto(model));
            return ResponseEntity.ok(response);
        }catch (NonExistenceException | DuplicatedException | InvalidBodyException e) {
            logger.info("ACCION CREATE ACCOUNT_HAS_ROLE -> Ha ocurrido un error al crear cuenta tiene usuario: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/account/{idAccount}/user/{idUser}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long idAccount, @PathVariable Long idUser) {
        logger.info("ACCION DELETEBYID ACCOUNT_HAS_ROLE -> Inicia eliminacion cuenta tiene usuario con id cuenta: " + idAccount + " | id usuario: " + idUser);
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            AccountHasUserId id = AccountHasUserId.builder()
                    .accountId(idAccount)
                    .userId(idUser)
                    .build();
            deleteByIdAccountHasUserUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }catch (NonExistenceException e) {
            logger.info("ACCION DELETEBYID ACCOUNT_HAS_ROLE -> Ha ocurrido un error al eliminar cuenta tiene usuario: " + e.getMessage());
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}

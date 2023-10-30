package com.sophie.store.backend.context.subcategory.presentation.controller;

import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryCreateDTO;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryResponseDTO;
import com.sophie.store.backend.context.subcategory.application.dto.SubcategoryUpdateDTO;
import com.sophie.store.backend.context.subcategory.application.usecase.*;
import com.sophie.store.backend.context.subcategory.domain.model.Subcategory;
import com.sophie.store.backend.context.subcategory.infrastructure.mappers.SubcategoryCreateMapper;
import com.sophie.store.backend.context.subcategory.infrastructure.mappers.SubcategoryResponseMapper;
import com.sophie.store.backend.context.subcategory.infrastructure.mappers.SubcategoryUpdateMapper;
import com.sophie.store.backend.utils.exceptions.*;
import com.sophie.store.backend.utils.http.HttpUtils;
import com.sophie.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subcategory")
@RequiredArgsConstructor
public class SubcategoryController {

    private final FindAllSubcategoryUseCase findAllSubcategoryUseCase;
    private final FindByIdSubcategoryUseCase findByIdSubcategoryUseCase;
    private final CreateSubcategoryUseCase createSubcategoryUseCase;
    private final UpdateSubcategoryUseCase updateSubcategoryUseCase;
    private final DeleteByIdSubcategoryUseCase deleteByIdSubcategoryUseCase;
    private final ChangeStateByIdSubcategoryUseCase changeStateByIdSubcategoryUseCase;

    private final SubcategoryCreateMapper subcategoryCreateMapper = new SubcategoryCreateMapper();
    private final SubcategoryUpdateMapper subcategoryUpdateMapper = new SubcategoryUpdateMapper();
    private final SubcategoryResponseMapper subcategoryResponseMapper = new SubcategoryResponseMapper();
    private final HttpUtils httpUtils = new HttpUtils();

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubcategoryResponseDTO>>> findAll() {
        ApiResponse<List<SubcategoryResponseDTO>> response = new ApiResponse<>();
        try {
            List<SubcategoryResponseDTO> subcategories = subcategoryResponseMapper.modelsToDtos(findAllSubcategoryUseCase.findAll());
            response.setData(subcategories);
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubcategoryResponseDTO>> findById(@PathVariable Long id) {
        ApiResponse<SubcategoryResponseDTO> response = new ApiResponse<>();
        try {
            Subcategory subcategory = findByIdSubcategoryUseCase.findById(id);
            response.setData(subcategoryResponseMapper.modelToDto(subcategory));
            return ResponseEntity.ok(response);
        } catch (NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PostMapping("/category/{idCategory}")
    public ResponseEntity<ApiResponse<SubcategoryResponseDTO>> create(@RequestBody SubcategoryCreateDTO subcategory, @PathVariable Long idCategory, @RequestHeader("Create-By") Long createBy) {
        ApiResponse<SubcategoryResponseDTO> response = new ApiResponse<>();
        try {
            subcategory.setCreateBy(createBy);
            response.setData(subcategoryResponseMapper.modelToDto(createSubcategoryUseCase.create(subcategoryCreateMapper.dtoToModel(subcategory), idCategory)));
            return ResponseEntity.ok(response);
        } catch (DuplicatedException | InvalidBodyException | NoResultsException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @PutMapping("/category/{idCategory}")
    public ResponseEntity<ApiResponse<SubcategoryResponseDTO>> update(@RequestBody SubcategoryUpdateDTO subcategory, @PathVariable Long idCategory, @RequestHeader("Update-By") Long updateBy) {
        ApiResponse<SubcategoryResponseDTO> response = new ApiResponse<>();
        try {
            subcategory.setUpdateBy(updateBy);
            response.setData(subcategoryResponseMapper.modelToDto(updateSubcategoryUseCase.update(subcategoryUpdateMapper.dtoToModel(subcategory), idCategory)));
            return ResponseEntity.ok(response);
        } catch (NoIdReceivedException | InvalidBodyException | NoResultsException | NoChangesException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) {
        ApiResponse<Object> response = new ApiResponse<>();
        try {
            deleteByIdSubcategoryUseCase.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (NonExisteceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<SubcategoryUpdateDTO>> changeStateById(@PathVariable Long id) {
        ApiResponse<SubcategoryUpdateDTO> response = new ApiResponse<>();
        try {
            Subcategory subcategory = changeStateByIdSubcategoryUseCase.changeStateById(id);
            response.setData(subcategoryUpdateMapper.modelToDto(subcategory));
            return ResponseEntity.ok(response);
        } catch (NonExisteceException e) {
            response.setError(httpUtils.determineErrorMessage(e));
            return new ResponseEntity<>(response, httpUtils.determineHttpStatus(e));
        }
    }

}

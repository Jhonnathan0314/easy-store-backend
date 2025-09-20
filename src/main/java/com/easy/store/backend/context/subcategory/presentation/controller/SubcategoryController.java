package com.easy.store.backend.context.subcategory.presentation.controller;

import com.easy.store.backend.context.subcategory.application.dto.SubcategoryCreateDTO;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryResponseDTO;
import com.easy.store.backend.context.subcategory.application.dto.SubcategoryUpdateDTO;
import com.easy.store.backend.context.subcategory.application.usecase.*;
import com.easy.store.backend.context.subcategory.domain.model.Subcategory;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryCreateMapper;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryResponseMapper;
import com.easy.store.backend.context.subcategory.infrastructure.mappers.SubcategoryUpdateMapper;
import com.easy.store.backend.utils.exceptions.*;
import com.easy.store.backend.utils.messages.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subcategory")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SubcategoryController {

    private final FindAllSubcategoryUseCase findAllSubcategoryUseCase;
    private final FindByIdSubcategoryUseCase findByIdSubcategoryUseCase;
    private final FindByAccountIdSubcategoryUseCase findByAccountIdSubcategoryUseCase;
    private final FindByCategoryIdSubcategoryUseCase findByCategoryIdSubcategoryUseCase;
    private final CreateSubcategoryUseCase createSubcategoryUseCase;
    private final UpdateSubcategoryUseCase updateSubcategoryUseCase;
    private final DeleteByIdSubcategoryUseCase deleteByIdSubcategoryUseCase;
    private final ChangeStateByIdSubcategoryUseCase changeStateByIdSubcategoryUseCase;

    private final SubcategoryCreateMapper subcategoryCreateMapper = new SubcategoryCreateMapper();
    private final SubcategoryUpdateMapper subcategoryUpdateMapper = new SubcategoryUpdateMapper();
    private final SubcategoryResponseMapper subcategoryResponseMapper = new SubcategoryResponseMapper();

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubcategoryResponseDTO>>> findAll() throws NoResultsException {
        ApiResponse<List<SubcategoryResponseDTO>> response = new ApiResponse<>();
        List<SubcategoryResponseDTO> subcategories = subcategoryResponseMapper.modelsToDtos(findAllSubcategoryUseCase.findAll());
        response.setData(subcategories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubcategoryResponseDTO>> findById(@PathVariable Long id) throws NoResultsException {
        ApiResponse<SubcategoryResponseDTO> response = new ApiResponse<>();
        Subcategory subcategory = findByIdSubcategoryUseCase.findById(id);
        response.setData(subcategoryResponseMapper.modelToDto(subcategory));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{idAccount}")
    public ResponseEntity<ApiResponse<List<SubcategoryResponseDTO>>> findByAccountId(@PathVariable Long idAccount) throws NoResultsException {
        ApiResponse<List<SubcategoryResponseDTO>> response = new ApiResponse<>();
        List<SubcategoryResponseDTO> subcategories = subcategoryResponseMapper.modelsToDtos(findByAccountIdSubcategoryUseCase.findByAccountId(idAccount));
        response.setData(subcategories);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{idCategory}")
    public ResponseEntity<ApiResponse<List<SubcategoryResponseDTO>>> findByCategoryId(@PathVariable Long idCategory) throws NoResultsException {
        ApiResponse<List<SubcategoryResponseDTO>> response = new ApiResponse<>();
        List<SubcategoryResponseDTO> subcategories = subcategoryResponseMapper.modelsToDtos(findByCategoryIdSubcategoryUseCase.findByCategoryId(idCategory));
        response.setData(subcategories);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<SubcategoryResponseDTO>> create(@RequestBody SubcategoryCreateDTO subcategory, @RequestHeader("Create-By") Long createBy) throws NoResultsException, InvalidBodyException {
        ApiResponse<SubcategoryResponseDTO> response = new ApiResponse<>();
        subcategory.setCreateBy(createBy);
        response.setData(subcategoryResponseMapper.modelToDto(createSubcategoryUseCase.create(subcategoryCreateMapper.dtoToModel(subcategory))));
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse<SubcategoryResponseDTO>> update(@RequestBody SubcategoryUpdateDTO subcategory, @RequestHeader("Update-By") Long updateBy) throws NoResultsException, NoIdReceivedException, NoChangesException, InvalidBodyException {
        ApiResponse<SubcategoryResponseDTO> response = new ApiResponse<>();
        subcategory.setUpdateBy(updateBy);
        response.setData(subcategoryResponseMapper.modelToDto(updateSubcategoryUseCase.update(subcategoryUpdateMapper.dtoToModel(subcategory))));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteById(@PathVariable Long id) throws NonExistenceException {
        ApiResponse<Object> response = new ApiResponse<>();
        deleteByIdSubcategoryUseCase.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/change-state/{id}")
    public ResponseEntity<ApiResponse<SubcategoryUpdateDTO>> changeStateById(@PathVariable Long id, @RequestHeader("Update-By") Long updateBy) throws NonExistenceException {
        ApiResponse<SubcategoryUpdateDTO> response = new ApiResponse<>();
        Subcategory subcategory = changeStateByIdSubcategoryUseCase.changeStateById(id, updateBy);
        response.setData(subcategoryUpdateMapper.modelToDto(subcategory));
        return ResponseEntity.ok(response);
    }

}

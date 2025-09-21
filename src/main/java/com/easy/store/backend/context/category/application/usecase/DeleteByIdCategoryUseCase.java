package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.s3.service.S3Service;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.constants.FileConstants;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteByIdCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final S3Service s3Service;

    public void deleteById(Long id) throws NonExistenceException {

        log.info("ACCION DELETEBYID CATEGORY -> Iniciando proceso con id: {}", id);

        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION DELETEBYID CATEGORY -> Categoria encontrada con éxito");

        log.info("ACCION DELETEBYID CATEGORY -> Eliminando categoria");

        categoryRepository.deleteById(id);

        if(!category.get().getImageName().equals(FileConstants.DEFAULT_CATEGORY_IMG)) {
            s3Service.deleteObject(category.get().getAccount().getId(), FileConstants.CATEGORY_CONTEXT, category.get().getImageName());
        }
    }

}

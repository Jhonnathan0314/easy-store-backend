package com.easy.store.backend.context.category.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
import com.easy.store.backend.context.s3.service.S3Service;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.constants.FileConstants;
import com.easy.store.backend.utils.exceptions.FileException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindByIdCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;
    private final S3Service s3Service;

    public Category findById(Long id, boolean image) throws NoResultsException, FileException {

        log.info("ACCION FINDBYID CATEGORY -> Iniciando búsqueda");

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDBYID CATEGORY -> Encontré categoria con éxito");

        Category category = optionalCategory.get();

        category.setPaymentTypes(categoryHasPaymentTypeRepository.findByCategoryId(category.getId()));
        if(image && !category.getImageName().equals(FileConstants.DEFAULT_CATEGORY_IMG)){
            Long accountId = category.getAccount().getId();
            category.setImage(s3Service.getObject(accountId, FileConstants.CATEGORY_CONTEXT, category.getImageName()));
        }

        return optionalCategory.get();
    }

}

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

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FindAllCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;
    private final S3Service s3Service;

    public List<Category> findAll(boolean images) throws NoResultsException, FileException {

        log.info("ACCION FINDALL CATEGORY -> Iniciando búsqueda");

        List<Category> categories = categoryRepository.findAll();
        if(categories == null || categories.isEmpty()) throw new NoResultsException(ErrorMessages.NO_RESULTS);
        log.info("ACCION FINDALL CATEGORY -> Encontré categorias con éxito");

        for (Category category : categories) {
            category.setPaymentTypes(categoryHasPaymentTypeRepository.findByCategoryId(category.getId()));
            if(images && !category.getImageName().equals(FileConstants.DEFAULT_CATEGORY_IMG)) {
                Long accountId = category.getAccount().getId();
                category.setImage(s3Service.getObject(accountId, FileConstants.CATEGORY_CONTEXT, category.getImageName()));
            }
        }

        return categories;
    }

}

package com.easy.store.backend.context.category_has_payment_type.application.usecase;


import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentType;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangeStateByIdCategoryHasPaymentTypeUseCase {

    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;

    public CategoryHasPaymentType changeStateByIdCategoryHasPaymentTypeUseCase(CategoryHasPaymentTypeId id) throws NonExistenceException {

        log.info("ACCION CHANGESTATEBYID CATEGORY HAS PAYMENT TYPE -> Iniciando proceso con id: {}", id);

        Optional<CategoryHasPaymentType> optCategory = categoryHasPaymentTypeRepository.findById(id);
        if(optCategory.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);
        log.info("ACCION CHANGESTATEBYID CATEGORY HAS PAYMENT TYPE -> Objeto encontrado con éxito");

        CategoryHasPaymentType category = optCategory.get();
        category.setState(category.getState().equals("active") ? "inactive" : "active");

        log.info("ACCION CHANGESTATEBYID CATEGORY HAS PAYMENT TYPE -> Actualizando estado");
        return categoryHasPaymentTypeRepository.update(category);

    }

}

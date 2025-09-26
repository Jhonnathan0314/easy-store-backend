package com.easy.store.backend.context.category_has_payment_type.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.category_has_payment_type.domain.model.CategoryHasPaymentTypeId;
import com.easy.store.backend.context.category_has_payment_type.domain.port.CategoryHasPaymentTypeRepository;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.NonExistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteCategoryHasPaymentTypeUseCase {

    private final CategoryHasPaymentTypeRepository categoryHasPaymentTypeRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    public void deleteById(Long categoryId, Long paymentTypeId) throws NonExistenceException {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if(categoryOpt.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);

        Optional<PaymentType> paymentTypeOpt = paymentTypeRepository.findById(paymentTypeId);
        if(paymentTypeOpt.isEmpty()) throw new NonExistenceException(ErrorMessages.NON_EXISTENT_DATA);

        CategoryHasPaymentTypeId id = CategoryHasPaymentTypeId.builder()
                .categoryId(categoryId)
                .paymentTypeId(paymentTypeId)
                .build();

        categoryHasPaymentTypeRepository.deleteById(id);
    }

}

package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.category.domain.port.CategoryRepository;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratePurchaseUseCase {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final CategoryRepository categoryRepository;

    public Purchase generate(Purchase purchase) throws InvalidBodyException, NoResultsException {

        log.info("ACCION GENERATE PRODUCT -> Iniciando proceso con body: {}", purchase.toString());

        if(!purchase.isValid()) throw new InvalidBodyException(ErrorMessages.INVALID_BODY);
        log.info("ACCION GENERATE PRODUCT -> Validé cuerpo de la petición");

        Optional<User> optUser = userRepository.findById(purchase.getUser().getId());
        if(optUser.isEmpty()) throw new NoResultsException(ErrorMessages.NO_USER_RESULTS);
        log.info("ACCION GENERATE PRODUCT -> Usuario encontrado con éxito");

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(purchase.getPaymentType().getId());
        if(optPaymentType.isEmpty()) throw new NoResultsException(ErrorMessages.NO_PAYMENT_TYPE_RESULTS);
        log.info("ACCION GENERATE PRODUCT -> Tipo de pago encontrado con éxito");

        Optional<Category> optCategory = categoryRepository.findById(purchase.getCategory().getId());
        if(optCategory.isEmpty()) throw new NoResultsException(ErrorMessages.NO_CATEGORY_RESULTS);
        log.info("ACCION GENERATE PRODUCT -> Categoria encontrada con éxito");

        purchase.setTotal(new BigDecimal("0"));
        purchase.setUser(optUser.get());
        purchase.setPaymentType(optPaymentType.get());
        purchase.setCategory(optCategory.get());

        log.info("ACCION GENERATE PRODUCT -> Generando compra");

        return purchaseRepository.generate(purchase);
    }

}

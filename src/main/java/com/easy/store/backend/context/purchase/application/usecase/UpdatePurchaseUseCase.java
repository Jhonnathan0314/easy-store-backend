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
import com.easy.store.backend.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UpdatePurchaseUseCase {

    private final Logger logger = Logger.getLogger(UpdatePurchaseUseCase.class.getName());

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final CategoryRepository categoryRepository;
    private final ErrorMessages errorMessages = new ErrorMessages();

    public Purchase update(Purchase purchase) throws
            NoIdReceivedException, InvalidBodyException, NoResultsException, NoChangesException, NonExistenceException {

        logger.info("ACCION UDPATE PURCHASE -> Inicia el proceso con body: " + purchase.toString());

        if(purchase.getId() == null) throw new NoIdReceivedException(errorMessages.NO_ID_RECEIVED);
        logger.info("ACCION UDPATE PURCHASE -> Validé id");

        Optional<Purchase> optPurchase = purchaseRepository.findById(purchase.getId());
        if(optPurchase.isEmpty()) throw new NonExistenceException(errorMessages.NON_EXISTENT_DATA);
        logger.info("ACCION UDPATE PURCHASE -> Validé existencia de la compra");

        if(!purchase.isValid(purchase)) throw new InvalidBodyException(errorMessages.INVALID_BODY);
        logger.info("ACCION UDPATE PURCHASE -> Validé cuerpo de la petición");

        Optional<User> optUser = userRepository.findById(purchase.getUser().getId());
        if(optUser.isEmpty()) throw new NoResultsException(errorMessages.NO_USER_RESULTS);
        logger.info("ACCION UDPATE PURCHASE -> Validé existencia del usuario");

        Optional<PaymentType> optPaymentType = paymentTypeRepository.findById(purchase.getPaymentType().getId());
        if(optPaymentType.isEmpty()) throw new NoResultsException(errorMessages.NO_PAYMENT_TYPE_RESULTS);
        logger.info("ACCION UDPATE PURCHASE -> Validé existencia del tipo de pago");

        Optional<Category> optCategory = categoryRepository.findById(purchase.getCategory().getId());
        if(optCategory.isEmpty()) throw new NoResultsException(errorMessages.NO_CATEGORY_RESULTS);
        logger.info("ACCION UDPATE PURCHASE -> Validé existencia de la categoria");

        if(!areDifferences(optPurchase.get(), purchase)) throw new NoChangesException(errorMessages.NO_CHANGES);
        logger.info("ACCION UDPATE PURCHASE -> Validé que hayan cambios a aplicar");

        purchase.setTotal(optPurchase.get().getTotal());
        purchase.setUser(optUser.get());
        purchase.setPaymentType(optPaymentType.get());
        purchase.setCategory(optCategory.get());

        logger.info("ACCION UDPATE PURCHASE -> Actualizando compra");

        return purchaseRepository.update(purchase);
    }

    private boolean areDifferences(Purchase purchaseDb, Purchase purchase) {
        return !purchaseDb.getState().equals(purchase.getState()) ||
                !purchaseDb.getUser().getId().equals(purchase.getUser().getId()) ||
                !purchaseDb.getPaymentType().getId().equals(purchase.getPaymentType().getId());
    }

}

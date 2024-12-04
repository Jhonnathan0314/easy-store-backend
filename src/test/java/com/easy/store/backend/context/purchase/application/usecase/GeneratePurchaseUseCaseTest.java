package com.easy.store.backend.context.purchase.application.usecase;

import com.easy.store.backend.context.payment_type.data.PaymentTypeData;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.context.purchase.data.PurchaseData;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.purchase.domain.port.PurchaseRepository;
import com.easy.store.backend.context.user.data.UserData;
import com.easy.store.backend.context.user.domain.port.UserRepository;
import com.easy.store.backend.utils.constants.ErrorMessages;
import com.easy.store.backend.utils.exceptions.DuplicatedException;
import com.easy.store.backend.utils.exceptions.InvalidBodyException;
import com.easy.store.backend.utils.exceptions.NoResultsException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeneratePurchaseUseCaseTest {

    @InjectMocks
    private GeneratePurchaseUseCase generatePurchaseUseCase;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    private static Long userId;
    private static Long paymentTypeId;
    private static PurchaseData purchaseData;
    private static UserData userData;
    private static PaymentTypeData paymentTypeData;
    private static ErrorMessages errorMessages;

    @BeforeAll
    static void setUp() {
        userId = 1L;
        paymentTypeId = 1L;
        purchaseData = new PurchaseData();
        userData = new UserData();
        paymentTypeData = new PaymentTypeData();
        errorMessages = new ErrorMessages();
    }

    @Test
    void generateSuccess() throws InvalidBodyException, NoResultsException {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserResponseOne()));
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeResponseOne()));
        when(purchaseRepository.generate(any(Purchase.class))).thenReturn(purchaseData.getPurchaseGenerateValid());

        Purchase response = generatePurchaseUseCase.generate(purchaseData.getPurchaseGenerateValid(), userId, paymentTypeId);

        assertNotNull(response);
        assertEquals(response, purchaseData.getPurchaseGenerateValid());

        verify(userRepository).findById(any(Long.class));
        verify(paymentTypeRepository).findById(any(Long.class));
        verify(purchaseRepository).generate(any(Purchase.class));
    }

    @Test
    void generateFailedInvalidBodyException() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserToUpdateNoId()));
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.of(paymentTypeData.getPaymentTypeResponseOne()));

        InvalidBodyException exception = assertThrows(
                InvalidBodyException.class,
                () -> generatePurchaseUseCase.generate(purchaseData.getPurchaseGenerateValid(), userId, paymentTypeId)
        );

        assertEquals(exception.getMessage(), errorMessages.INVALID_BODY);

        verify(purchaseRepository, never()).generate(any(Purchase.class));
    }

    @Test
    void generateFailedNoResultsExceptionByUser() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> generatePurchaseUseCase.generate(purchaseData.getPurchaseResponseOne(), userId, paymentTypeId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_USER_RESULTS);

        verify(purchaseRepository, never()).generate(any(Purchase.class));
    }

    @Test
    void generateFailedNoResultsExceptionByPaymentType() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userData.getUserResponseOne()));
        when(paymentTypeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        NoResultsException exception = assertThrows(
                NoResultsException.class,
                () -> generatePurchaseUseCase.generate(purchaseData.getPurchaseResponseOne(), userId, paymentTypeId)
        );

        assertEquals(exception.getMessage(), errorMessages.NO_PAYMENT_TYPE_RESULTS);

        verify(purchaseRepository, never()).generate(any(Purchase.class));
    }
}
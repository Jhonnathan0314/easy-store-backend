package com.easy.store.backend.context.purchase.presentation.controller;

import com.easy.store.backend.context.category.domain.model.Category;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.purchase.application.dto.PurchaseGenerateDTO;
import com.easy.store.backend.context.purchase.application.service.PurchaseAuthorizationService;
import com.easy.store.backend.context.purchase.application.usecase.*;
import com.easy.store.backend.context.purchase.domain.model.Purchase;
import com.easy.store.backend.context.user.domain.model.User;
import com.easy.store.backend.utils.exceptions.ForbiddenActionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Verifies that {@link PurchaseController} delegates authorization to
 * {@link PurchaseAuthorizationService} BEFORE invoking the underlying use case.
 * <p>
 * These tests represent the "fix checking" side (a {@link ForbiddenActionException} from the
 * authorization service must short-circuit the request, never reaching the use case) and the
 * "preservation" side (when authorization passes, the existing use-case wiring and response
 * shape are unchanged).
 */
@ExtendWith(MockitoExtension.class)
class PurchaseControllerTest {

    @Mock private FindAllPurchaseUseCase findAllPurchaseUseCase;
    @Mock private FindByIdPurchaseUseCase findByIdPurchaseUseCase;
    @Mock private FindByAccountIdPurchaseUseCase findByAccountIdPurchaseUseCase;
    @Mock private FindByCategoryIdPurchaseUseCase findByCategoryIdPurchaseUseCase;
    @Mock private FindByUserIdPurchaseUseCase findByUserIdPurchaseUseCase;
    @Mock private GeneratePurchaseUseCase generatePurchaseUseCase;
    @Mock private UpdatePurchaseUseCase updatePurchaseUseCase;
    @Mock private DeleteByIdPurchaseUseCase deleteByIdPurchaseUseCase;
    @Mock private PurchaseAuthorizationService purchaseAuthorizationService;

    @InjectMocks
    private PurchaseController controller;

    private Purchase purchase(Long id) {
        return Purchase.builder()
                .id(id)
                .user(User.builder().id(5L).build())
                .paymentType(PaymentType.builder().id(1L).build())
                .category(Category.builder().id(1L).build())
                .total(BigDecimal.ZERO)
                .state("active")
                .products(List.of())
                .build();
    }

    // --- Fix checking: authorization denial must block the use case entirely ---

    @Test
    void findAll_forbidden_neverCallsUseCase() throws Exception {
        doThrow(new ForbiddenActionException("forbidden")).when(purchaseAuthorizationService).authorizeFindAll();

        assertThatThrownBy(controller::findAll).isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(findAllPurchaseUseCase);
    }

    @Test
    void findById_forbidden_neverCallsUseCase() throws Exception {
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizePurchaseAccess(eq(12L), anyString());

        assertThatThrownBy(() -> controller.findById(12L)).isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(findByIdPurchaseUseCase);
    }

    @Test
    void findByAccountId_forbidden_neverCallsUseCase() throws Exception {
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizeFindByAccountId(2L);

        assertThatThrownBy(() -> controller.findByAccountId(2L)).isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(findByAccountIdPurchaseUseCase);
    }

    @Test
    void findByCategoryId_forbidden_neverCallsUseCase() throws Exception {
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizeFindByCategoryId(100L);

        assertThatThrownBy(() -> controller.findByCategoryId(100L)).isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(findByCategoryIdPurchaseUseCase);
    }

    @Test
    void findByUserId_forbidden_neverCallsUseCase() throws Exception {
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizeFindByUserId(9L);

        assertThatThrownBy(() -> controller.findByUserId(9L)).isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(findByUserIdPurchaseUseCase);
    }

    @Test
    void generate_forbidden_neverCallsUseCase() throws Exception {
        PurchaseGenerateDTO dto = PurchaseGenerateDTO.builder().userId(9L).paymentTypeId(1L).categoryId(1L).build();
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizeGenerate(9L);

        assertThatThrownBy(() -> controller.generate(dto, 5L)).isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(generatePurchaseUseCase);
    }

    // --- Preservation: authorization passing must not change existing behavior ---

    @Test
    void findAll_allowed_delegatesToUseCase() throws Exception {
        when(findAllPurchaseUseCase.findAll()).thenReturn(List.of(purchase(1L)));

        assertThatCode(controller::findAll).doesNotThrowAnyException();

        verify(purchaseAuthorizationService).authorizeFindAll();
        verify(findAllPurchaseUseCase).findAll();
    }

    @Test
    void findById_allowed_delegatesToUseCase() throws Exception {
        when(findByIdPurchaseUseCase.findById(12L)).thenReturn(purchase(12L));

        assertThatCode(() -> controller.findById(12L)).doesNotThrowAnyException();

        verify(purchaseAuthorizationService).authorizePurchaseAccess(eq(12L), anyString());
        verify(findByIdPurchaseUseCase).findById(12L);
    }

    @Test
    void generate_allowed_delegatesToUseCase() throws Exception {
        PurchaseGenerateDTO dto = PurchaseGenerateDTO.builder().userId(5L).paymentTypeId(1L).categoryId(1L).build();
        when(generatePurchaseUseCase.generate(any())).thenReturn(purchase(1L));

        assertThatCode(() -> controller.generate(dto, 5L)).doesNotThrowAnyException();

        verify(purchaseAuthorizationService).authorizeGenerate(5L);
        verify(generatePurchaseUseCase).generate(any());
    }
}

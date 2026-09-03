package com.easy.store.backend.context.purchase_has_product.presentation.controller;

import com.easy.store.backend.context.purchase.application.service.PurchaseAuthorizationService;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductAddDTO;
import com.easy.store.backend.context.purchase_has_product.application.dto.PurchaseHasProductUpdateDTO;
import com.easy.store.backend.context.purchase_has_product.application.usecase.*;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProduct;
import com.easy.store.backend.context.purchase_has_product.domain.model.PurchaseHasProductId;
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
 * Verifies that {@link PurchaseHasProductController} delegates ownership checks to
 * {@link PurchaseAuthorizationService} BEFORE mutating any purchase line, and that legitimate
 * (preservation) requests continue to reach the use cases unchanged.
 */
@ExtendWith(MockitoExtension.class)
class PurchaseHasProductControllerTest {

    @Mock private AddPurchaseHasProductUseCase addPurchaseHasProductUseCase;
    @Mock private AddAllPurchaseHasProductUseCase addAllPurchaseHasProductUseCase;
    @Mock private UpdatePurchaseHasProductUseCase updatePurchaseHasProductUseCase;
    @Mock private RemoveByIdPurchaseHasProductUseCase removeByIdPurchaseHasProductUseCase;
    @Mock private RemoveAllPurchaseHasProductUseCase removeAllPurchaseHasProductUseCase;
    @Mock private PurchaseAuthorizationService purchaseAuthorizationService;

    @InjectMocks
    private PurchaseHasProductController controller;

    private PurchaseHasProductId id(Long purchaseId, Long productId) {
        return PurchaseHasProductId.builder().purchaseId(purchaseId).productId(productId).build();
    }

    private PurchaseHasProduct line(Long purchaseId, Long productId) {
        return PurchaseHasProduct.builder()
                .id(id(purchaseId, productId))
                .quantity(1)
                .unitPrice(BigDecimal.TEN)
                .subtotal(BigDecimal.TEN)
                .build();
    }

    // --- Fix checking: a foreign purchaseId must block the operation entirely ---

    @Test
    void add_forbidden_neverCallsUseCase() throws Exception {
        PurchaseHasProductAddDTO dto = PurchaseHasProductAddDTO.builder().id(id(2L, 1L)).quantity(1).build();
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizePurchaseAccess(eq(2L), anyString());

        assertThatThrownBy(() -> controller.add(dto)).isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(addPurchaseHasProductUseCase);
    }

    @Test
    void update_forbidden_neverCallsUseCase() throws Exception {
        PurchaseHasProductUpdateDTO dto = PurchaseHasProductUpdateDTO.builder().id(id(2L, 1L)).quantity(1).build();
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizePurchaseAccess(eq(2L), anyString());

        assertThatThrownBy(() -> controller.update(dto)).isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(updatePurchaseHasProductUseCase);
    }

    @Test
    void deleteByPurchaseIdAndProductId_forbidden_neverCallsUseCase() throws Exception {
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizePurchaseAccess(eq(2L), anyString());

        assertThatThrownBy(() -> controller.deleteByPurchaseIdAndProductId(2L, 1L))
                .isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(removeByIdPurchaseHasProductUseCase);
    }

    @Test
    void addAll_forbiddenBecauseOneForeignLine_rejectsWholeBatch() throws Exception {
        List<PurchaseHasProductAddDTO> dtos = List.of(
                PurchaseHasProductAddDTO.builder().id(id(1L, 1L)).quantity(1).build(),
                PurchaseHasProductAddDTO.builder().id(id(2L, 2L)).quantity(1).build()
        );
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizePurchaseHasProductIds(anyList());

        assertThatThrownBy(() -> controller.addAll(dtos)).isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(addAllPurchaseHasProductUseCase);
    }

    @Test
    void removeAll_forbiddenBecauseOneForeignLine_rejectsWholeBatch() throws Exception {
        List<PurchaseHasProductId> ids = List.of(id(1L, 1L), id(2L, 2L));
        doThrow(new ForbiddenActionException("forbidden"))
                .when(purchaseAuthorizationService).authorizePurchaseHasProductIds(ids);

        assertThatThrownBy(() -> controller.deleteByPurchaseIdAndProductId(ids))
                .isInstanceOf(ForbiddenActionException.class);

        verifyNoInteractions(removeAllPurchaseHasProductUseCase);
    }

    // --- Preservation: legitimate (own-purchase) operations continue to work ---

    @Test
    void add_allowed_delegatesToUseCase() throws Exception {
        PurchaseHasProductAddDTO dto = PurchaseHasProductAddDTO.builder().id(id(1L, 1L)).quantity(1).build();
        when(addPurchaseHasProductUseCase.add(any())).thenReturn(line(1L, 1L));

        assertThatCode(() -> controller.add(dto)).doesNotThrowAnyException();

        verify(purchaseAuthorizationService).authorizePurchaseAccess(eq(1L), anyString());
        verify(addPurchaseHasProductUseCase).add(any());
    }

    @Test
    void addAll_allowed_delegatesToUseCase() throws Exception {
        List<PurchaseHasProductAddDTO> dtos = List.of(
                PurchaseHasProductAddDTO.builder().id(id(1L, 1L)).quantity(1).build()
        );
        when(addAllPurchaseHasProductUseCase.addAll(any())).thenReturn(List.of(line(1L, 1L)));

        assertThatCode(() -> controller.addAll(dtos)).doesNotThrowAnyException();

        verify(purchaseAuthorizationService).authorizePurchaseHasProductIds(anyList());
        verify(addAllPurchaseHasProductUseCase).addAll(any());
    }

    @Test
    void deleteByPurchaseIdAndProductId_allowed_delegatesToUseCase() throws Exception {
        assertThatCode(() -> controller.deleteByPurchaseIdAndProductId(1L, 1L)).doesNotThrowAnyException();

        verify(purchaseAuthorizationService).authorizePurchaseAccess(eq(1L), anyString());
        verify(removeByIdPurchaseHasProductUseCase).removeByPurchaseIdAndProductId(any());
    }
}

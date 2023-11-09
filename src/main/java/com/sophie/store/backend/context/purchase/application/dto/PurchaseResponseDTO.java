package com.sophie.store.backend.context.purchase.application.dto;

import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.sophie.store.backend.context.user.application.dto.UserResponseDTO;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseResponseDTO {
    private Long id;
    private UserResponseDTO user;
    private PaymentTypeResponseDTO paymentType;
    private Timestamp date;
    private BigDecimal total;
}

package com.sophie.store.backend.context.purchase.application.dto;

import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeDTO;
import com.sophie.store.backend.context.user.application.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
    private Long id;
    private UserDTO user;
    private PaymentTypeDTO paymentType;
    private Timestamp date;
    private BigDecimal total;
    private Long createBy;
}

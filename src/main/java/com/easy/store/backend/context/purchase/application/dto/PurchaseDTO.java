package com.easy.store.backend.context.purchase.application.dto;

import com.easy.store.backend.context.category.application.dto.CategoryDTO;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeDTO;
import com.easy.store.backend.context.user.application.dto.UserDTO;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseDTO {
    private Long id;
    private UserDTO user;
    private PaymentTypeDTO paymentType;
    private CategoryDTO category;
    private Timestamp date;
    private BigDecimal total;
    private String state;
    private Long createBy;
    private Long updateBy;
    private Timestamp creationDate;
    private Timestamp updateDate;
}

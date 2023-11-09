package com.sophie.store.backend.context.purchase.data;

import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeDTO;
import com.sophie.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.sophie.store.backend.context.purchase.application.dto.PurchaseDTO;
import com.sophie.store.backend.context.purchase.application.dto.PurchaseGenerateDTO;
import com.sophie.store.backend.context.purchase.application.dto.PurchaseResponseDTO;
import com.sophie.store.backend.context.purchase.domain.model.Purchase;
import com.sophie.store.backend.context.purchase.infrastructure.persistence.PurchaseEntity;
import com.sophie.store.backend.context.roles.application.dto.RoleCreateDTO;
import com.sophie.store.backend.context.roles.application.dto.RoleDTO;
import com.sophie.store.backend.context.roles.application.dto.RoleResponseDTO;
import com.sophie.store.backend.context.roles.domain.model.Role;
import com.sophie.store.backend.context.roles.infrastructure.persistence.RoleEntity;
import com.sophie.store.backend.context.user.application.dto.UserCreateDTO;
import com.sophie.store.backend.context.user.application.dto.UserDTO;
import com.sophie.store.backend.context.user.application.dto.UserResponseDTO;
import com.sophie.store.backend.context.user.domain.model.User;
import com.sophie.store.backend.context.user.infrastructure.persistence.UserEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
public class PurchaseData {

    //Correct information
    private final Purchase purchaseResponseOne = Purchase.builder()
            .id(1L)
            .user(User.builder()
                    .id(1L)
                    .username("test@test.com")
                    .name("test")
                    .lastName("test")
                    .password("12345")
                    .role(Role.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .paymentType(PaymentType.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .date(Timestamp.valueOf(LocalDateTime.now()))
            .total(new BigDecimal(2000))
            .build();

    private final Purchase purchaseResponseTwo = Purchase.builder()
            .id(2L)
            .user(User.builder()
                    .id(1L)
                    .username("test@test.com")
                    .name("test")
                    .lastName("test")
                    .password("12345")
                    .role(Role.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .paymentType(PaymentType.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .date(Timestamp.valueOf(LocalDateTime.now()))
            .total(new BigDecimal(3000))
            .build();

    private final Purchase purchaseEmpty = Purchase.builder()
            .id(1L)
            .build();

    private final Purchase purchaseGenerateValid = Purchase.builder()
            .user(User.builder()
                    .id(1L)
                    .username("test@test.com")
                    .name("test")
                    .lastName("test")
                    .password("12345")
                    .role(Role.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .paymentType(PaymentType.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .build();

    private final Purchase purchaseGenerateInvalid = Purchase.builder().build();

    private List<Purchase> purchasesList;

    //To mapper test
    private final PurchaseEntity purchaseEntity = PurchaseEntity.builder()
            .id(1L)
            .user(UserEntity.builder()
                    .id(1L)
                    .username("test@test.com")
                    .name("test")
                    .lastName("test")
                    .password("12345")
                    .role(RoleEntity.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .paymentType(PaymentTypeEntity.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .date(Timestamp.valueOf(LocalDateTime.now()))
            .total(new BigDecimal(2000))
            .build();

    private final PurchaseDTO purchaseDTO = PurchaseDTO.builder()
            .id(1L)
            .user(UserDTO.builder()
                    .id(1L)
                    .username("test@test.com")
                    .name("test")
                    .lastName("test")
                    .password("12345")
                    .role(RoleDTO.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .paymentType(PaymentTypeDTO.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .date(Timestamp.valueOf(LocalDateTime.now()))
            .total(new BigDecimal(2000))
            .build();

    private final PurchaseGenerateDTO purchaseGenerateDTO = PurchaseGenerateDTO.builder()
            .user(UserCreateDTO.builder()
                    .username("test@test.com")
                    .name("test")
                    .lastName("test")
                    .password("12345")
                    .build())
            .paymentType(PaymentTypeCreateDTO.builder()
                    .name("test")
                    .build())
            .build();

    private final PurchaseResponseDTO purchaseResponseDTO = PurchaseResponseDTO.builder()
            .id(1L)
            .user(UserResponseDTO.builder()
                    .id(1L)
                    .username("test@test.com")
                    .name("test")
                    .lastName("test")
                    .role(RoleResponseDTO.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .paymentType(PaymentTypeResponseDTO.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .date(Timestamp.valueOf(LocalDateTime.now()))
            .total(new BigDecimal(1000))
            .build();

    private final Purchase purchaseModel = Purchase.builder()
            .id(1L)
            .user(User.builder()
                    .id(1L)
                    .username("test@test.com")
                    .name("test")
                    .lastName("test")
                    .role(Role.builder()
                            .id(1L)
                            .name("test")
                            .build())
                    .build())
            .paymentType(PaymentType.builder()
                    .id(1L)
                    .name("test")
                    .build())
            .date(Timestamp.valueOf(LocalDateTime.now()))
            .total(new BigDecimal(1000))
            .build();

    private Timestamp fromDate;
    private Timestamp toDate;

    private BigDecimal fromTotal;
    private BigDecimal toTotal;

    public PurchaseData() {
        purchasesList = new LinkedList<>();
        purchasesList.add(purchaseResponseOne);
        purchasesList.add(purchaseResponseTwo);

        fromDate = Timestamp.valueOf(LocalDateTime.now());
        toDate = Timestamp.valueOf(LocalDateTime.now());

        fromTotal = new BigDecimal(1000);
        toTotal = new BigDecimal(2000);
    }
}

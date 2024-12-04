package com.easy.store.backend.context.payment_type.data;

import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeCreateDTO;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeDTO;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeResponseDTO;
import com.easy.store.backend.context.payment_type.application.dto.PaymentTypeUpdateDTO;
import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class PaymentTypeData {

    //Correct information
    private final PaymentType paymentTypeActive = PaymentType.builder()
            .id(1L)
            .name("test")
            .state("active")
            .build();

    private final PaymentType paymentTypeInactive = PaymentType.builder()
            .id(1L)
            .name("test")
            .state("inactive")
            .build();

    private final PaymentType paymentTypeEmpty = PaymentType.builder()
            .id(1L)
            .name("")
            .state("")
            .build();

    private final PaymentType paymentTypeCreateValid = PaymentType.builder()
            .name("test")
            .build();

    private final PaymentType paymentTypeCreateInvalid = PaymentType.builder().build();

    private final PaymentType paymentTypeToUpdate = PaymentType.builder()
            .id(1L)
            .name("update")
            .build();

    private final PaymentType paymentTypeUpdated = PaymentType.builder()
            .id(1L)
            .name("update")
            .build();

    private final PaymentType paymentTypeToUpdateNoId = PaymentType.builder()
            .name("update")
            .build();

    private final PaymentType paymentTypeToUpdateInvalid = PaymentType.builder()
            .id(1L)
            .build();

    private final PaymentType paymentTypeResponseOne = PaymentType.builder()
            .id(1L)
            .name("test")
            .build();

    private final PaymentType paymentTypeResponseTwo = PaymentType.builder()
            .id(2L)
            .name("test2")
            .build();

    private final String encodedPassword = "12345ENCODED";

    private List<PaymentType> paymentTypesList;
    
    //To mapper test
    private final PaymentTypeEntity paymentTypeEntity = PaymentTypeEntity.builder()
            .id(1L)
            .name("test")
            .state("active")
            .build();

    private final PaymentTypeDTO paymentTypeDTO = PaymentTypeDTO.builder()
            .id(1L)
            .name("test")
            .build();

    private final PaymentTypeCreateDTO paymentTypeCreateDTO = PaymentTypeCreateDTO.builder()
            .name("test")
            .build();

    private final PaymentTypeUpdateDTO paymentTypeUpdateDTO = PaymentTypeUpdateDTO.builder()
            .id(1L)
            .name("test")
            .build();

    private final PaymentTypeResponseDTO paymentTypeResponseDTO = PaymentTypeResponseDTO.builder()
            .id(1L)
            .name("test")
            .build();

    private final PaymentType paymentTypeModel = PaymentType.builder()
            .id(1L)
            .name("test")
            .state("active")
            .build();

    public PaymentTypeData() {
        paymentTypesList = new LinkedList<>();
        paymentTypesList.add(paymentTypeResponseOne);
        paymentTypesList.add(paymentTypeResponseTwo);
    }
}

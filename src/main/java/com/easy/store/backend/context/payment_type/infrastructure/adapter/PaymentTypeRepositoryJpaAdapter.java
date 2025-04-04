package com.easy.store.backend.context.payment_type.infrastructure.adapter;

import com.easy.store.backend.context.payment_type.domain.model.PaymentType;
import com.easy.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeCreateMapper;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeMapper;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeResponseMapper;
import com.easy.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeUpdateMapper;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.easy.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentTypeRepositoryJpaAdapter implements PaymentTypeRepository {

    private final PaymentTypeJpaRepository paymentTypeJpaRepository;
    private final PaymentTypeCreateMapper createMapper = new PaymentTypeCreateMapper();
    private final PaymentTypeUpdateMapper updateMapper = new PaymentTypeUpdateMapper();
    private final PaymentTypeResponseMapper responseMapper = new PaymentTypeResponseMapper();

    @Override
    public List<PaymentType> findAll() {
        List<PaymentTypeEntity> paymentTypeEntities = paymentTypeJpaRepository.findAll();
        return responseMapper.entitiesToModels(paymentTypeEntities);
    }

    @Override
    public Optional<PaymentType> findById(Long id) {
        Optional<PaymentTypeEntity> optPaymentTypeEntity = paymentTypeJpaRepository.findById(id);
        return optPaymentTypeEntity.map(responseMapper::entityToModel);
    }

    @Override
    public List<PaymentType> findByAccountId(Long accountId) {
        List<PaymentTypeEntity> paymentTypeEntities = paymentTypeJpaRepository.findByAccountId(accountId);
        return responseMapper.entitiesToModels(paymentTypeEntities);
    }

    @Override
    public PaymentType create(PaymentType paymentType) {
        PaymentTypeEntity paymentTypeEntity = paymentTypeJpaRepository.save(createMapper.modelToEntity(paymentType));
        return responseMapper.entityToModel(paymentTypeEntity);
    }

    @Override
    public PaymentType update(PaymentType paymentType) {
        PaymentTypeEntity paymentTypeEntity = paymentTypeJpaRepository.save(updateMapper.modelToEntity(paymentType));
        return responseMapper.entityToModel(paymentTypeEntity);
    }

    @Override
    public void deleteById(Long id) { paymentTypeJpaRepository.deleteById(id); }
}

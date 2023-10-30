package com.sophie.store.backend.context.payment_type.infrastructure.adapter;

import com.sophie.store.backend.context.payment_type.domain.model.PaymentType;
import com.sophie.store.backend.context.payment_type.domain.port.PaymentTypeRepository;
import com.sophie.store.backend.context.payment_type.infrastructure.mappers.PaymentTypeMapper;
import com.sophie.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeEntity;
import com.sophie.store.backend.context.payment_type.infrastructure.persistence.PaymentTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentTypeRepositoryAdapter implements PaymentTypeRepository {

    private final PaymentTypeJpaRepository paymentTypeJpaRepository;
    private final PaymentTypeMapper mapper = new PaymentTypeMapper();

    @Override
    public List<PaymentType> findAll() {
        List<PaymentTypeEntity> paymentTypeEntities = paymentTypeJpaRepository.findAll();
        return mapper.entitiesToModels(paymentTypeEntities);
    }

    @Override
    public Optional<PaymentType> findById(Long id) {
        Optional<PaymentTypeEntity> optPaymentTypeEntity = paymentTypeJpaRepository.findById(id);
        return optPaymentTypeEntity.map(mapper::entityToModel);
    }

    @Override
    public Optional<PaymentType> findByName(String name) {
        Optional<PaymentTypeEntity> optPaymentTypeEntity = paymentTypeJpaRepository.findByName(name);
        return optPaymentTypeEntity.map(mapper::entityToModel);
    }

    @Override
    public PaymentType create(PaymentType paymentType) {
        PaymentTypeEntity paymentTypeEntity = paymentTypeJpaRepository.save(mapper.modelToEntity(paymentType));
        return mapper.entityToModel(paymentTypeEntity);
    }

    @Override
    public PaymentType update(PaymentType paymentType) {
        PaymentTypeEntity paymentTypeEntity = paymentTypeJpaRepository.save(mapper.modelToEntity(paymentType));
        return mapper.entityToModel(paymentTypeEntity);
    }

    @Override
    public void deleteById(Long id) { paymentTypeJpaRepository.deleteById(id); }
}

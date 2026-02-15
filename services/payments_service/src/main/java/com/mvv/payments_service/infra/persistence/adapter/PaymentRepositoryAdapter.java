package com.mvv.payments_service.infra.persistence.adapter;

import com.mvv.payments_service.domain.model.Payment;
import com.mvv.payments_service.domain.repository.PaymentRepositoryPort;
import com.mvv.payments_service.infra.persistence.entity.PaymentEntity;
import com.mvv.payments_service.infra.persistence.mapper.PaymentPersistenceMapper;
import com.mvv.payments_service.infra.persistence.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentPersistenceMapper mapper;


    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = mapper.toEntity(payment);
        return mapper.toDomain(paymentJpaRepository.save(paymentEntity));
    }

    @Override
    public Optional<Payment> findById(UUID uuid) {
        return paymentJpaRepository.findById(uuid).map(mapper::toDomain);
    }
}

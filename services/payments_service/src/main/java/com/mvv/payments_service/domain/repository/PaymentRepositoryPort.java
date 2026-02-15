package com.mvv.payments_service.domain.repository;

import com.mvv.payments_service.domain.model.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepositoryPort {

    Payment save(Payment payment);
    Optional<Payment> findById(UUID uuid);

}

package com.mvv.payments_service.infra.persistence.mapper;

import com.mvv.payments_service.domain.model.Payment;
import com.mvv.payments_service.infra.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentPersistenceMapper {

    public PaymentEntity toEntity(Payment payment) {

        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(payment.getId());
        paymentEntity.setOrderId(payment.getOrderId());
        paymentEntity.setKeycloakUserId(payment.getKeycloakUserId());
        paymentEntity.setCardId(payment.getCardId());
        paymentEntity.setAmount(payment.getAmount());
        paymentEntity.setStatus(payment.getStatus());

        return paymentEntity;

    }

    public Payment toDomain (PaymentEntity payment) {
        return Payment.restore(payment.getId(), payment.getOrderId(), payment.getAmount(), payment.getKeycloakUserId(),
                payment.getCardId(), payment.getStatus());

    }

}

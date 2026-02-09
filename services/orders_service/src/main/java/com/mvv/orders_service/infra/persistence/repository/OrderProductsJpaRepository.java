package com.mvv.orders_service.infra.persistence.repository;

import com.mvv.orders_service.infra.persistence.entity.OrderProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderProductsJpaRepository extends JpaRepository<OrderProductsEntity, UUID> {

    List<OrderProductsEntity> findByOrderId(UUID orderId);
    boolean existsByOrderId(UUID orderId);

}

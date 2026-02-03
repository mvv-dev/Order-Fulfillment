package com.mvv.orders_service.domain.repository;

import com.mvv.orders_service.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {

    Order save(Order order);
    Optional<Order> findByid(UUID id);
    List<Order> search();

}

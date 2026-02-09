package com.mvv.orders_service.infra.persistence.adapter;

import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.domain.repository.OrderRepositoryPort;
import com.mvv.orders_service.infra.persistence.entity.OrderEntity;
import com.mvv.orders_service.infra.persistence.entity.OrderProductsEntity;
import com.mvv.orders_service.infra.persistence.mapper.OrderPersistenceMapper;
import com.mvv.orders_service.infra.persistence.mapper.OrderProductsPersistenceMapper;
import com.mvv.orders_service.infra.persistence.repository.OrderJpaRepository;
import com.mvv.orders_service.infra.persistence.repository.OrderProductsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderProductsJpaRepository orderProductsJpaRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;
    private final OrderProductsPersistenceMapper orderProductsPersistenceMapper;


    @Override
    @Transactional
    public Order save(Order order) {

        OrderEntity orderEntity = orderPersistenceMapper.toEntity(order);
        OrderEntity savedOrderEntity = orderJpaRepository.save(orderEntity);

        if (!orderProductsJpaRepository.existsByOrderId(order.getId())) {
            var items = order.getItems().stream()
                    .map(item -> orderProductsPersistenceMapper.toEntity(order.getId(), item))
                    .toList();
            orderProductsJpaRepository.saveAll(items);
        }

        var savedItems = orderProductsJpaRepository.findByOrderId(order.getId());

        return orderPersistenceMapper.toDomain(savedOrderEntity, savedItems);

    }


    @Override
    public Optional<Order> findById(UUID id) {
        return orderJpaRepository.findById(id).map(orderEntity -> {
            List<OrderProductsEntity> items =
                    orderProductsJpaRepository.findByOrderId(id);

            return orderPersistenceMapper.toDomain(orderEntity, items);
        });
    }

    @Override
    public List<Order> search() {
        return List.of();
    }
}

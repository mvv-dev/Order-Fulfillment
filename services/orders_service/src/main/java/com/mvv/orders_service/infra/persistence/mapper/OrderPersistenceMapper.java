package com.mvv.orders_service.infra.persistence.mapper;

import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.infra.persistence.entity.OrderEntity;
import com.mvv.orders_service.infra.persistence.entity.OrderProductsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderPersistenceMapper {

    private final OrderProductsPersistenceMapper orderProductsMapper;

    public OrderEntity toEntity(Order order) {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId());
        orderEntity.setStatus(order.getStatus());
        orderEntity.setTotalAmount(order.getTotalAmount());
        orderEntity.setKeycloakUserId(order.getKeycloakUserId());
        orderEntity.setCardId(order.getCardId());

        return orderEntity;

    }

    public Order toDomain(OrderEntity orderEntity, List<OrderProductsEntity> orderProductsEntity) {

        return Order.restore(
                orderEntity.getId(), orderEntity.getStatus(), orderEntity.getTotalAmount(),
                orderEntity.getKeycloakUserId(), orderEntity.getCardId(),
                orderProductsEntity.stream().map(orderProductsMapper::toDomain).toList()
        );

    }


}

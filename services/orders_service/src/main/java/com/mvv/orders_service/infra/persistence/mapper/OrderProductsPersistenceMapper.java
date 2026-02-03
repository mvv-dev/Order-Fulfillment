package com.mvv.orders_service.infra.persistence.mapper;

import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.domain.model.OrderItem;
import com.mvv.orders_service.infra.persistence.entity.OrderProductsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderProductsPersistenceMapper {

    public OrderProductsEntity toEntity(UUID orderId, OrderItem item) {

        OrderProductsEntity orderProductsEntity = new OrderProductsEntity();
        orderProductsEntity.setOrderId(orderId);
        orderProductsEntity.setProductId(item.getProductId());
        orderProductsEntity.setProductPrice(item.getPrice());
        orderProductsEntity.setProductName(item.getName());
        orderProductsEntity.setProductQuantity(item.getQuantity());

        return orderProductsEntity;

    }

    public OrderItem toDomain(OrderProductsEntity orderProductsEntity) {

        return OrderItem.restore(
                orderProductsEntity.getProductId(), orderProductsEntity.getProductName(),
                orderProductsEntity.getProductPrice(), orderProductsEntity.getProductQuantity()
        );

    }

}

package com.mvv.orders_service.infra.clients.mapper;

import com.mvv.orders_service.domain.model.OrderItem;
import com.mvv.orders_service.infra.clients.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductToOrderItemMapper {

    public OrderItem toOrderItem(ProductDTO dto, Integer quantity) {
        return OrderItem.create(dto.id(), dto.name(), dto.price(), quantity);
    }

}

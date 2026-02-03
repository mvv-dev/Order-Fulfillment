package com.mvv.orders_service.application.usecase;

import com.mvv.orders_service.application.usecase.command.CreateOrderCommand;
import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.domain.model.OrderItem;
import com.mvv.orders_service.infra.clients.ProductsResourceClient;
import com.mvv.orders_service.infra.clients.dto.ProductDTO;
import com.mvv.orders_service.infra.clients.mapper.ProductToOrderItemMapper;
import com.mvv.orders_service.infra.persistence.adapter.OrderRepositoryAdapter;
import com.mvv.orders_service.infra.persistence.entity.OrderProductsEntity;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final ProductsResourceClient productsResourceClient;
    private final OrderRepositoryAdapter orderRepositoryAdapter;
    private final ProductToOrderItemMapper productToOrderItemMapper;


    public Order execute(CreateOrderCommand command) {

        //Initial code, without card validation and rabbitmq
        //Only basic products check

        try {

            List<OrderItem> orderItems = new ArrayList<>();

            for(var orderItemCommand : command.items()) {

                ProductDTO productDTO = productsResourceClient.productsData(orderItemCommand.name());
                OrderItem orderItem = OrderItem.create(productDTO.id(),
                        productDTO.name(), productDTO.price(), orderItemCommand.quantity());

                orderItems.add(orderItem);
            }

            Order orderToSave = new Order(command.keycloakUserId(), command.cardId(), orderItems);
            return orderRepositoryAdapter.save(orderToSave);


        } catch (FeignException.FeignClientException.FeignClientException e) {
            int status = e.status();
            if (status == HttpStatus.NOT_FOUND.value()) {
                throw new IllegalArgumentException("To make an order, it needs to have existents products");
            }
            throw new RuntimeException("Error on Microservices communication");
        }

    }

}

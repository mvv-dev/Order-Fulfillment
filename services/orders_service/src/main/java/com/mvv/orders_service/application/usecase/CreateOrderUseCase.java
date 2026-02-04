package com.mvv.orders_service.application.usecase;

import com.mvv.orders_service.application.mapper.OrderEventMapper;
import com.mvv.orders_service.application.usecase.command.CreateOrderCommand;
import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.domain.model.OrderItem;
import com.mvv.orders_service.infra.amqp.dto.OrderSolicitedEventDTO;
import com.mvv.orders_service.infra.amqp.publisher.OrderEventPublisher;
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
    private final OrderEventPublisher orderEventPublisher;
    private final OrderEventMapper orderEventMapper;


    public Order execute(CreateOrderCommand command) {

        //Initial code, without card validation and rabbitmq
        //Only basic products check
        OrderSolicitedEventDTO solicitedEventDTO = orderEventMapper.toOrderSolicitedEventDTO(command);
        System.out.println("Tentaiva de publicar uma mensagem para saga");
        orderEventPublisher.publishOrderSolicited(solicitedEventDTO);

        return null;

    }

}

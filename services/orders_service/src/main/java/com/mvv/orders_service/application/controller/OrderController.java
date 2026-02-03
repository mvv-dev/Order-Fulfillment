package com.mvv.orders_service.application.controller;

import com.mvv.orders_service.application.controller.dto.HttpCreateOrderDTO;
import com.mvv.orders_service.application.usecase.CreateOrderUseCase;
import com.mvv.orders_service.application.usecase.command.CreateOrderCommand;
import com.mvv.orders_service.application.usecase.command.CreateOrderItemCommand;
import com.mvv.orders_service.domain.model.Order;
import com.mvv.orders_service.domain.repository.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final OrderRepositoryPort orderRepositoryPort;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody HttpCreateOrderDTO dto, @AuthenticationPrincipal Jwt jwt) {

        UUID sub = UUID.fromString(jwt.getSubject());
        List<CreateOrderItemCommand> cmdItems = dto.products().stream().map(
                itemDTO -> new CreateOrderItemCommand(itemDTO.name(), itemDTO.quantity())
        ).toList();
        var cmdOrder = new CreateOrderCommand(sub, dto.cardId(), cmdItems);
        Order orderSaved = createOrderUseCase.execute(cmdOrder);

        return ResponseEntity.ok().build();

    }

}

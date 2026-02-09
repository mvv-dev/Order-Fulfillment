package com.mvv.orders_service.application.controller;

import com.mvv.orders_service.application.controller.dto.HttpCreateOrderDTO;
import com.mvv.orders_service.application.payload.event.orders_solicited.ItemsSolicited;
import com.mvv.orders_service.application.usecase.CreateOrderUseCase;
import com.mvv.orders_service.application.usecase.SolicitOrderUseCase;
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
    private final SolicitOrderUseCase solicitOrderUseCase;
    private final OrderRepositoryPort orderRepositoryPort;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody HttpCreateOrderDTO dto, @AuthenticationPrincipal Jwt jwt) {

        UUID sub = UUID.fromString(jwt.getSubject());
        UUID cardId = dto.cardId();

        List<ItemsSolicited> items = dto.products().stream().
                map(httpProductDTO -> new ItemsSolicited(httpProductDTO.name(), httpProductDTO.quantity()))
                .toList();

        solicitOrderUseCase.execute(sub, cardId, items);

        return ResponseEntity.ok().build();

    }

}

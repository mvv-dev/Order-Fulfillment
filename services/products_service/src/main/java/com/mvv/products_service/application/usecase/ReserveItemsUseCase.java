package com.mvv.products_service.application.usecase;

import com.mvv.products_service.application.payload.command.products_reserve_inventory.ProductsReserveInventory;
import com.mvv.products_service.application.payload.common.Card;
import com.mvv.products_service.application.payload.common.Customer;
import com.mvv.products_service.application.payload.common.MessageType;
import com.mvv.products_service.application.payload.common.StatusProduct;
import com.mvv.products_service.application.payload.common.items.ItemsOrder;
import com.mvv.products_service.application.payload.common.envelope.Envelope;
import com.mvv.products_service.application.payload.common.ItemsReserved;
import com.mvv.products_service.application.payload.event.products_inventory_reserved.ProductsInventoryReserved;
import com.mvv.products_service.application.payload.event.products_inventory_reserved.ReservationError;
import com.mvv.products_service.application.port.out.ProductsEventPublisherPort;
import com.mvv.products_service.domain.model.Product;
import com.mvv.products_service.domain.repository.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReserveItemsUseCase {

    private final ProductsEventPublisherPort publisherPort;
    private final ProductRepositoryPort productRepositoryPort;

    public void execute(Envelope<ProductsReserveInventory> envelope) {

        log.info("Starting stock reservation check: {}", envelope.payload());

        List<ItemsOrder> itemsEnvelope = envelope.payload().items();
        ProductsReserveInventory commandPayload = envelope.payload();

        List<ReservationError> errors = new ArrayList<>();
        List<ItemsReserved> itemsReserved = new ArrayList<>();
        StatusProduct statusReservation = StatusProduct.FAILED;

        for (var item : itemsEnvelope) {

            Optional<Product> productOptional = productRepositoryPort.findByName(item.name());

            // Check if all items are available before start reservation

            if (productOptional.isPresent()) {

                if (item.requestedQuantity() > item.quantityLeft()) {
                    errors.add(new ReservationError(item.name(), "OUT_OF_STOCK",
                            "Insufficient stock", item.quantityLeft()));

                }

            } else {
                log.info("Unexpected Error. This product is not in stock: {}", item.name());
                throw new RuntimeException("This product is not in stock: " + item.name());
            }
        }

        if (errors.isEmpty()) {

            statusReservation = StatusProduct.OK;

            for (var item : itemsEnvelope) {
                Optional<Product> productOptional = productRepositoryPort.findByName(item.name());
                Product product = productOptional.get();
                product.updateQuantity(item.requestedQuantity());
                productRepositoryPort.save(product);
                itemsReserved.add(new ItemsReserved(item.productId(), item.name(), item.requestedQuantity()));
            }


        }


        ProductsInventoryReserved eventPayload = new ProductsInventoryReserved(
                commandPayload.requestId(),new Customer(commandPayload.customer().keycloakUserId()),
                new Card(commandPayload.card().cardId()), commandPayload.amount(), commandPayload.status(), itemsReserved,
                errors, statusReservation
        );

        Envelope<ProductsInventoryReserved> eventEnvelope = new Envelope<>(
            UUID.randomUUID(), "event.products.inventory_reserved", MessageType.EVENT,
                Instant.now(), envelope.correlationId(), envelope.messageId(), "products-source",
                eventPayload
        );

        log.info("The stock reservation check was complete. A new message to confirm order will be published: {}",
                eventPayload);
        publisherPort.publish(eventEnvelope);

    }

}

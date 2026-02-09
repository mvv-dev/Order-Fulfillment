package com.mvv.products_service.application.usecase;

import com.mvv.products_service.application.payload.command.products_reserve_inventory.ProductsReserveInventory;
import com.mvv.products_service.application.payload.common.Card;
import com.mvv.products_service.application.payload.common.Customer;
import com.mvv.products_service.application.payload.common.MessageType;
import com.mvv.products_service.application.payload.common.StatusProduct;
import com.mvv.products_service.application.payload.common.items.ItemsError;
import com.mvv.products_service.application.payload.common.items.ItemsOrder;
import com.mvv.products_service.application.payload.common.envelope.Envelope;
import com.mvv.products_service.application.payload.event.products_inventory_reserved.ItemsReserved;
import com.mvv.products_service.application.payload.event.products_inventory_reserved.ProductsInventoryReserved;
import com.mvv.products_service.application.payload.event.products_inventory_reserved.ReservationError;
import com.mvv.products_service.application.port.out.ProductsEventPublisherPort;
import com.mvv.products_service.domain.model.Product;
import com.mvv.products_service.domain.repository.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReserveItemsUseCase {

    private final ProductsEventPublisherPort publisherPort;
    private final ProductRepositoryPort productRepositoryPort;

    public void execute(Envelope<ProductsReserveInventory> envelope) {

        List<ItemsOrder> itemsEnvelope = envelope.payload().items();
        ProductsReserveInventory commandPayload = envelope.payload();

        List<ReservationError> errors = new ArrayList<>();
        List<ItemsReserved> itemsReserved = new ArrayList<>();

        for (var item : itemsEnvelope) {

            Optional<Product> productOptional = productRepositoryPort.findByName(item.name());

            if (productOptional.isPresent()) {

                Product product = productOptional.get();

                if (item.requestedQuantity() > item.quantityLeft()) {
                    errors.add(new ReservationError(item.name(), "OUT_OF_STOCK",
                            "Insufficient stock", item.quantityLeft()));
                } else {
                    product.updateQuantity(item.requestedQuantity());
                    productRepositoryPort.save(product);
                    itemsReserved.add(new ItemsReserved(item.productId(), item.name(), item.requestedQuantity()));
                }

            } else {
                System.out.println("ERROR! Produto inválido");
            }
        }

        StatusProduct statusReservation = errors.isEmpty() ? StatusProduct.OK : StatusProduct.FAILED;
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

        publisherPort.publish(eventEnvelope);

    }

}

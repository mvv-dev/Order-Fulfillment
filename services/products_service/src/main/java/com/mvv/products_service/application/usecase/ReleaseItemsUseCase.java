package com.mvv.products_service.application.usecase;

import com.mvv.products_service.application.payload.command.products_release_items.ProductsReleaseItems;
import com.mvv.products_service.application.payload.common.ItemsReserved;
import com.mvv.products_service.application.payload.common.MessageType;
import com.mvv.products_service.application.payload.common.envelope.Envelope;
import com.mvv.products_service.application.payload.event.products_items_released.ItemsReleased;
import com.mvv.products_service.application.payload.event.products_items_released.ProductsItemsReleased;
import com.mvv.products_service.application.port.out.ProductsEventPublisherPort;
import com.mvv.products_service.domain.model.Product;
import com.mvv.products_service.domain.repository.ProductRepositoryPort;
import com.mvv.products_service.infra.persistence.adapter.ProductRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReleaseItemsUseCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final ProductRepositoryAdapter productRepositoryAdapter;
    private final ProductsEventPublisherPort productsEventPublisherPort;

    public void execute(Envelope<ProductsReleaseItems> envelope) {

        ProductsReleaseItems payload = envelope.payload();

        List<ItemsReserved> itemsReserved = payload.reservations();
        List<ItemsReleased> itemsReleased = new ArrayList<>();

        for(ItemsReserved itemReserved : itemsReserved) {

            Optional<Product> productOptional = productRepositoryPort.findById(itemReserved.productId());

            if (productOptional.isPresent()) {

                Product product = productOptional.get();
                product.releaseQuantity(itemReserved.reservedQuantity());
                Product productUpdated = productRepositoryAdapter.save(product);

                itemsReleased.add(
                        new ItemsReleased(productUpdated.getId(), productUpdated.getName(), productUpdated.getQuantityLeft())
                );

            } else {
                log.warn("Unexpected error, product id was not found");
            }

        }

        ProductsItemsReleased eventPayload = new ProductsItemsReleased(
                payload.requestId(), payload.customer(), payload.card(), payload.amount(), payload.statusOrder(),
                itemsReleased, payload.statusPayment(), payload.totalDebited()
        );

        Envelope<ProductsItemsReleased> eventEnvelope = new Envelope<>(
                UUID.randomUUID(), "event.products.items_released", MessageType.EVENT, Instant.now(),
                envelope.correlationId(), envelope.messageId(), "products-source", eventPayload
        );

        productsEventPublisherPort.publish(eventEnvelope);

    }

}

package com.mvv.products_service.application.usecase;

import com.mvv.products_service.application.payload.common.*;
import com.mvv.products_service.application.payload.common.envelope.Envelope;
import com.mvv.products_service.application.payload.command.products_check_items.ProductsCheckItems;
import com.mvv.products_service.application.payload.common.items.ItemsError;
import com.mvv.products_service.application.payload.common.items.ItemsOrder;
import com.mvv.products_service.application.payload.common.items.ItemsSolicited;
import com.mvv.products_service.application.payload.event.products_items_checked.*;
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
public class CheckItemsUseCase {

    private final ProductsEventPublisherPort productsEventPublisherPort;
    private final ProductRepositoryPort productRepositoryPort;

    public void execute(Envelope<ProductsCheckItems> envelope) {

        log.info("Starting the item check: {}", envelope.payload());

        List<ItemsSolicited> envProductsList = envelope.payload().items();
        ProductsCheckItems envPayload = envelope.payload();

        List<ItemsError> errors = new ArrayList<>();
        List<ItemsOrder> itemsOrder = new ArrayList<>();

        for (ItemsSolicited envProduct : envProductsList) {

            Optional<Product> productFound = productRepositoryPort.findByName(envProduct.name());
            if (productFound.isEmpty()) {
                errors.add(new ItemsError(envProduct.name(), "NOT_FOUND", "Product not found"));
            } else {
                Product product = productFound.get();
                itemsOrder.add(new ItemsOrder(product.getId(), product.getName(), product.getPrice(),
                        product.getQuantityLeft(), envProduct.quantity()));
            }

        }

        StatusProduct statusProduct = errors.isEmpty() ? StatusProduct.OK : StatusProduct.FAILED;

        Envelope<ProductsItemsChecked> envelopeItemsChecked = new Envelope<>(
                UUID.randomUUID(), "event.products.items_checked", MessageType.EVENT, Instant.now(),
                envelope.correlationId(), envelope.messageId(), "products-service",
                new ProductsItemsChecked(envPayload.requestId(), new Customer(envPayload.customer().keycloakUserId()),
                        new Card(envPayload.card().cardId()), statusProduct, itemsOrder, errors)
        );
        log.info("The item check was completed. A new message to create will be published: {}",
                envelopeItemsChecked.payload());
        productsEventPublisherPort.publish(envelopeItemsChecked);



    }

}

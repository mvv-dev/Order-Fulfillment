package com.mvv.saga_service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mvv.saga_service.application.contracts.common.Envelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductsItemsReleasedHandler {

    public void handle(Envelope<JsonNode> envelope) {

        log.info("Order products were sucessfully released on inventory");

    }

}

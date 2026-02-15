package com.mvv.saga_service.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrdersInvalidatedHandler {

    public void handle() {
        log.info("Order was succefully cancelled.");
    }

}

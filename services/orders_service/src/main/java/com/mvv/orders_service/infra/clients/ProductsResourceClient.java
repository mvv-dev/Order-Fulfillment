package com.mvv.orders_service.infra.clients;

import com.mvv.orders_service.infra.clients.config.FeignAuthForwardConfig;
import com.mvv.orders_service.infra.clients.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "products-service", path = "/products", configuration = FeignAuthForwardConfig.class)
public interface ProductsResourceClient {

    @GetMapping(params = "name")
    ProductDTO productsData(@RequestParam("name") String name);

}

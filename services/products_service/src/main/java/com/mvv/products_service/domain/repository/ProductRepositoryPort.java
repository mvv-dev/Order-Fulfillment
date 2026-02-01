package com.mvv.products_service.domain.repository;

import com.mvv.products_service.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryPort {

    Product save(Product product);
    Optional<Product> findById(UUID id);
    List<Product> search();
    Optional<Product> findByName(String name);

}

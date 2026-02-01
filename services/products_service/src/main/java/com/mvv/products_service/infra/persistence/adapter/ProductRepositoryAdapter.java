package com.mvv.products_service.infra.persistence.adapter;

import com.mvv.products_service.domain.model.Product;
import com.mvv.products_service.domain.repository.ProductRepositoryPort;
import com.mvv.products_service.infra.persistence.entity.ProductEntity;
import com.mvv.products_service.infra.persistence.mapper.ProductPersistenceMapper;
import com.mvv.products_service.infra.persistence.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductPersistenceMapper productPersistenceMapper;
    private final ProductJpaRepository productJpaRepository;


    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productPersistenceMapper.toEntity(product);
        ProductEntity productSaved = productJpaRepository.save(productEntity);
        return productPersistenceMapper.toDomain(productSaved);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productJpaRepository.findById(id).map(productPersistenceMapper::toDomain);
    }

    @Override
    public List<Product> search() {

        List<ProductEntity> productEntityList = productJpaRepository.findAll();
        return productEntityList.stream().map(
                productPersistenceMapper::toDomain
        ).toList();

    }

    @Override
    public Optional<Product> findByName(String name) {
        return productJpaRepository.findByName(name).map(productPersistenceMapper::toDomain);
    }
}

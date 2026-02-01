package com.mvv.products_service.infra.persistence.mapper;

import com.mvv.products_service.domain.model.Product;
import com.mvv.products_service.infra.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceMapper {

    public ProductEntity toEntity(Product product) {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setQuantity_left(product.getQuantityLeft());

        return productEntity;

    }

    public Product toDomain(ProductEntity productEntity) {

        return Product.restore(
                productEntity.getId(), productEntity.getName(),
                productEntity.getPrice(), productEntity.getQuantity_left()
        );

    }

}

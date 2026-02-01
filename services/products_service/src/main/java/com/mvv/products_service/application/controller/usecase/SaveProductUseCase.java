package com.mvv.products_service.application.controller.usecase;

import com.mvv.products_service.application.controller.usecase.command.SaveProductCommand;
import com.mvv.products_service.application.exception.DuplicateRegisterException;
import com.mvv.products_service.domain.model.Product;
import com.mvv.products_service.domain.repository.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public Product execute(SaveProductCommand productCommand) {

        Optional<Product> alreadyExists = productRepositoryPort.findByName(productCommand.name());
        if (alreadyExists.isPresent()) {
            throw new DuplicateRegisterException("Product already exists");
        }
        Product productToSave = new Product(productCommand.name(), productCommand.price(),
                productCommand.quantityLeft());

        return productRepositoryPort.save(productToSave);

    }

}

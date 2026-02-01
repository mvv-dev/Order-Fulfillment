package com.mvv.products_service.application.controller;

import com.mvv.products_service.application.controller.usecase.SaveProductUseCase;
import com.mvv.products_service.application.controller.usecase.command.SaveProductCommand;
import com.mvv.products_service.application.dto.HttpProductSearchResponseDTO;
import com.mvv.products_service.application.dto.HttpSaveProductDTO;
import com.mvv.products_service.application.mapper.ProductMapper;
import com.mvv.products_service.domain.model.Product;
import com.mvv.products_service.domain.repository.ProductRepositoryPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.PortUnreachableException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper;
    private final ProductRepositoryPort productRepositoryPort;
    private final SaveProductUseCase productUseCase;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid HttpSaveProductDTO saveProductDTO) {

        SaveProductCommand productCommand = productMapper.toCommand(saveProductDTO);
        Product productSaved = productUseCase.execute(productCommand);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productSaved.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("id/{id}")
    public ResponseEntity<HttpProductSearchResponseDTO> findById(@PathVariable("id") String id) {

        UUID productId = UUID.fromString(id);
        Optional<Product> product = productRepositoryPort.findById(productId);

        if (product.isEmpty()) return ResponseEntity.notFound().build();

        Product productFound = product.get();
        HttpProductSearchResponseDTO responseDTO = productMapper.toDto(productFound);

        return ResponseEntity.ok(responseDTO);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("name/{name}")
    public ResponseEntity<HttpProductSearchResponseDTO> findByName(@PathVariable("name") String name) {

        Optional<Product> product = productRepositoryPort.findByName(name);

        if (product.isEmpty()) return ResponseEntity.notFound().build();

        Product productFound = product.get();
        HttpProductSearchResponseDTO responseDTO = productMapper.toDto(productFound);

        return ResponseEntity.ok(responseDTO);

    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<HttpProductSearchResponseDTO>> search() {

        return ResponseEntity.ok(productRepositoryPort.search().stream()
                .map(productMapper::toDto).toList());

    }

}

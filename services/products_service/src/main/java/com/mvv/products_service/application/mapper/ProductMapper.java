package com.mvv.products_service.application.mapper;

import com.mvv.products_service.application.controller.usecase.command.SaveProductCommand;
import com.mvv.products_service.application.dto.HttpProductSearchResponseDTO;
import com.mvv.products_service.application.dto.HttpSaveProductDTO;
import com.mvv.products_service.domain.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    SaveProductCommand toCommand(HttpSaveProductDTO dto);
    HttpProductSearchResponseDTO toDto(Product product);

}

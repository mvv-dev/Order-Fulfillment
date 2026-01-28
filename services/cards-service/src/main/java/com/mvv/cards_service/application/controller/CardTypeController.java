package com.mvv.cards_service.application.controller;

import com.mvv.cards_service.application.dto.HttpCreateCardTypeDTO;
import com.mvv.cards_service.application.mapper.CardTypeMapper;
import com.mvv.cards_service.application.usecase.CreateCardTypeUseCase;
import com.mvv.cards_service.application.usecase.command.CreateCardTypeCommand;
import com.mvv.cards_service.domain.model.CardType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("card-types")
@RequiredArgsConstructor
public class CardTypeController {

    private final CardTypeMapper mapper;
    private final CreateCardTypeUseCase createCardTypeUseCase;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid HttpCreateCardTypeDTO createCardTypeDTO) {

        CreateCardTypeCommand cardType = mapper.toDomain(createCardTypeDTO);
        System.out.println(cardType.name() + " | " + cardType.brand() + " | " + cardType.initialBalance());
        CardType  cardTypeCreated = createCardTypeUseCase.execute(cardType);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardTypeCreated.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

}

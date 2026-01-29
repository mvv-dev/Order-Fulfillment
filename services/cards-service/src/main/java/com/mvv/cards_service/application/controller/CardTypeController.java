package com.mvv.cards_service.application.controller;

import com.mvv.cards_service.application.dto.HttpCreateCardTypeDTO;
import com.mvv.cards_service.application.dto.HttpResponseCreateCardTypeDTO;
import com.mvv.cards_service.application.mapper.CardTypeMapper;
import com.mvv.cards_service.application.usecase.CreateCardTypeUseCase;
import com.mvv.cards_service.application.usecase.command.CreateCardTypeCommand;
import com.mvv.cards_service.domain.model.CardType;
import com.mvv.cards_service.domain.repository.CardTypeRepositoryPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("card-types")
@RequiredArgsConstructor
public class CardTypeController {

    private final CardTypeMapper mapper;
    private final CreateCardTypeUseCase createCardTypeUseCase;
    private final CardTypeRepositoryPort cardTypeRepositoryPort;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid HttpCreateCardTypeDTO createCardTypeDTO) {

        CreateCardTypeCommand cardType = mapper.toDomain(createCardTypeDTO);
        CardType  cardTypeCreated = createCardTypeUseCase.execute(cardType);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardTypeCreated.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponseCreateCardTypeDTO> findById(@PathVariable("id") String id) {

        var cardType = cardTypeRepositoryPort.findById(UUID.fromString(id)).orElse(null);
        if (cardType == null) return ResponseEntity.notFound().build();
        HttpResponseCreateCardTypeDTO responseCreateCardTypeDTO = new HttpResponseCreateCardTypeDTO(
                cardType.getId(), cardType.getName(), cardType.getBrand(), cardType.getInitialBalance()
        );

        return ResponseEntity.ok(responseCreateCardTypeDTO);

    }

    @GetMapping("/{name}")
    public ResponseEntity<HttpResponseCreateCardTypeDTO> findByName(@PathVariable("name") String name) {

        var cardType = cardTypeRepositoryPort.findByName(name).orElse(null);
        if (cardType == null) return ResponseEntity.notFound().build();
        HttpResponseCreateCardTypeDTO responseCreateCardTypeDTO = new HttpResponseCreateCardTypeDTO(
                cardType.getId(), cardType.getName(), cardType.getBrand(), cardType.getInitialBalance()
        );

        return ResponseEntity.ok(responseCreateCardTypeDTO);

    }

    @GetMapping
    public ResponseEntity<List<HttpResponseCreateCardTypeDTO>> search() {

        List<CardType> cardTypes = cardTypeRepositoryPort.search();
        List<HttpResponseCreateCardTypeDTO> cardsTypesResponseDto = cardTypes.stream().map(
                cardType -> new HttpResponseCreateCardTypeDTO(
                        cardType.getId(), cardType.getName(), cardType.getBrand(), cardType.getInitialBalance()
                )
        ).toList();

        return ResponseEntity.ok(cardsTypesResponseDto);

    }



}

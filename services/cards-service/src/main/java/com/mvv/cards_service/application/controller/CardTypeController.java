package com.mvv.cards_service.application.controller;

import com.mvv.cards_service.application.dto.HttpCreateCardTypeDTO;
import com.mvv.cards_service.application.dto.HttpResponseSearchCardTypeDTO;
import com.mvv.cards_service.application.mapper.CardTypeMapper;
import com.mvv.cards_service.application.usecase.CreateCardTypeUseCase;
import com.mvv.cards_service.application.usecase.command.CreateCardTypeCommand;
import com.mvv.cards_service.domain.model.CardType;
import com.mvv.cards_service.domain.repository.CardTypeRepositoryPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("card-types")
@RequiredArgsConstructor
public class CardTypeController {

    private final CardTypeMapper mapper;
    private final CreateCardTypeUseCase createCardTypeUseCase;
    private final CardTypeRepositoryPort cardTypeRepositoryPort;

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<HttpResponseSearchCardTypeDTO> findById(@PathVariable("id") String id) {

        var cardType = cardTypeRepositoryPort.findById(UUID.fromString(id)).orElse(null);
        if (cardType == null) return ResponseEntity.notFound().build();
        HttpResponseSearchCardTypeDTO responseCreateCardTypeDTO = new HttpResponseSearchCardTypeDTO(
                cardType.getId(), cardType.getName(), cardType.getBrand(), cardType.getInitialBalance()
        );

        return ResponseEntity.ok(responseCreateCardTypeDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<HttpResponseSearchCardTypeDTO>> search() {

        List<CardType> cardTypes = cardTypeRepositoryPort.search();
        List<HttpResponseSearchCardTypeDTO> cardsTypesResponseDto = cardTypes.stream().map(
                cardType -> new HttpResponseSearchCardTypeDTO(
                        cardType.getId(), cardType.getName(), cardType.getBrand(), cardType.getInitialBalance()
                )
        ).toList();

        return ResponseEntity.ok(cardsTypesResponseDto);

    }



}

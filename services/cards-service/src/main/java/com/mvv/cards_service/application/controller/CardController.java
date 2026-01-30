package com.mvv.cards_service.application.controller;

import com.mvv.cards_service.application.dto.HttpIssueCardDTO;
import com.mvv.cards_service.application.dto.HttpResponseSearchCardDTO;
import com.mvv.cards_service.application.usecase.IssueCardUseCase;
import com.mvv.cards_service.application.usecase.command.IssueCardCommand;
import com.mvv.cards_service.domain.model.Card;
import com.mvv.cards_service.domain.repository.CardRepositoryPort;
import com.mvv.cards_service.domain.repository.CardTypeRepositoryPort;
import com.mvv.cards_service.infra.persistence.adapter.CardRepositoryAdapter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("card")
@RequiredArgsConstructor
public class CardController {

    private final CardRepositoryPort cardRepositoryPort;
    private final IssueCardUseCase issueCardUseCase;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/{client}")
    public ResponseEntity<Void> issueCard(@PathVariable("client") String customerSub,
            @AuthenticationPrincipal Jwt jwt, @RequestBody @Valid HttpIssueCardDTO createCardDTO) {

        String actorSub = jwt.getSubject();
        var cmd = new IssueCardCommand(actorSub, customerSub, createCardDTO.cardTypeId());
        Card cardCreated = issueCardUseCase.execute(cmd);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cardCreated.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<HttpResponseSearchCardDTO> findById(@PathVariable("id") String id) {

        UUID cardId = UUID.fromString(id);
        Optional<Card> cardFound = cardRepositoryPort.findById(cardId);
        if (cardFound.isEmpty()) return ResponseEntity.notFound().build();
        Card card = cardFound.get();

        HttpResponseSearchCardDTO responseSearchCard = new HttpResponseSearchCardDTO(
                card.getId(), card.getType(), card.getKeycloakUserId(), card.getBalance()
        );

        return ResponseEntity.ok(responseSearchCard);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<HttpResponseSearchCardDTO>> search() {

        List<Card> cards = cardRepositoryPort.search();
        List<HttpResponseSearchCardDTO> cardDTOS = cards.stream().map(
                card -> new HttpResponseSearchCardDTO(card.getId(), card.getType(),
                        card.getKeycloakUserId(), card.getBalance())
        ).toList();

        return ResponseEntity.ok(cardDTOS);

    }

}

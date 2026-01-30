package com.mvv.cards_service.application.usecase.command;

import java.util.UUID;

public record IssueCardCommand(
        String actorSub,
        String customerSub,
        UUID cardTypeId
) {
}

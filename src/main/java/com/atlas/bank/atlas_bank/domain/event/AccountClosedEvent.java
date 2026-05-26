package com.atlas.bank.atlas_bank.domain.event;

public record AccountClosedEvent(
        Long accountId,
        String accountNumber,
        String ownerName
) {
}

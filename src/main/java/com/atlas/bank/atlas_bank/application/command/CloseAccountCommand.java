package com.atlas.bank.atlas_bank.application.command;

import lombok.Builder;

@Builder
public record CloseAccountCommand(
        Long accountId
) {
}

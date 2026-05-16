package com.atlas.bank.atlas_bank.application.query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionReadModel(
        Long transactionId,
        String type,
        Long sourceAccountId,
        Long targetAccountId,
        BigDecimal amount,
        BigDecimal fee,
        String status,
        LocalDateTime createdAt
) {
}

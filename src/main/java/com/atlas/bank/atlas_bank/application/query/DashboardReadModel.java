package com.atlas.bank.atlas_bank.application.query;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record DashboardReadModel(
        Long accountId,
        String accountNumber,
        String ownerName,
        String type,
        BigDecimal balance,
        String status,
        List<TransactionReadModel> recentTransactions
) {
}

package com.btg.pactual.fundsapi.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionDTO(
        String transactionId,
        String fundName,
        String type,
        BigDecimal amount,
        Instant timestamp
) {
}
package io.hhplus.tdd.point.domain.model;

import io.hhplus.tdd.point.domain.enums.TransactionType;

public record PointHistory(
        long id,
        long userId,
        long amount,
        TransactionType type,
        long updateMillis
) {
}

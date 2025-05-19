package io.hhplus.tdd.point.adapter.out.persistence;

import io.hhplus.tdd.point.domain.enums.TransactionType;

public record PointHistoryModel(
        long id,
        long userId,
        long amount,
        TransactionType type,
        long updateMillis
) {
}

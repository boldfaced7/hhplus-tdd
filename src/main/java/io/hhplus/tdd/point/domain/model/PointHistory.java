package io.hhplus.tdd.point.domain.model;

import io.hhplus.tdd.point.domain.enums.TransactionType;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.PointHistoryId;
import io.hhplus.tdd.point.domain.vo.UpdateMillis;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public record PointHistory(
        PointHistoryId pointHistoryId,
        UserPointId userPointId,
        Amount amount,
        TransactionType transactionType,
        UpdateMillis updateMillis
) {

    public static PointHistory of(
            UserPointId userPointId,
            Amount amount,
            TransactionType transactionType
    ) {
        return new PointHistory(
                null,
                userPointId,
                amount,
                transactionType,
                null
        );
    }

    public static PointHistory of(
            PointHistoryId pointHistoryId,
            UserPointId userPointId,
            Amount amount,
            TransactionType transactionType,
            UpdateMillis updateMillis
    ) {
        return new PointHistory(
                pointHistoryId,
                userPointId,
                amount,
                transactionType,
                updateMillis
        );
    }
}

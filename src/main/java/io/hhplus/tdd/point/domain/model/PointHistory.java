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

    public static PointHistory charge(UserPointId userId, Amount amount) {
        return new PointHistory(
            null,
            userId,
            amount,
            TransactionType.CHARGE,
            new UpdateMillis(System.currentTimeMillis())
        );
    }

    public static PointHistory use(UserPointId userId, Amount amount) {
        return new PointHistory(
            null,
            userId,
            amount,
            TransactionType.USE,
            new UpdateMillis(System.currentTimeMillis())
        );
    }
}

package io.hhplus.tdd.point.domain.model;

import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.Point;
import io.hhplus.tdd.point.domain.vo.UpdateMillis;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public record UserPoint(
        UserPointId userPointId,
        Point point,
        UpdateMillis updateMillis
) {

    public static UserPoint of(
            Point point
    ) {
        return new UserPoint(
                null,
                point,
                null
        );
    }

    public static UserPoint of(
            UserPointId userPointId,
            Point point,
            UpdateMillis updateMillis
    ) {
        return new UserPoint(
                userPointId,
                point,
                updateMillis
        );
    }

    public UserPoint chargePoint(Amount amount) {
        return new UserPoint(
                userPointId,
                point.add(amount),
                updateMillis
        );
    }

    public UserPoint usePoint(Amount amount) {
        return new UserPoint(
                userPointId,
                point.subtract(amount),
                updateMillis
        );
    }

}

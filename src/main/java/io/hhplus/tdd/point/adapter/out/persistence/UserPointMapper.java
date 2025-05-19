package io.hhplus.tdd.point.adapter.out.persistence;

import io.hhplus.tdd.point.domain.model.UserPoint;
import io.hhplus.tdd.point.domain.vo.Point;
import io.hhplus.tdd.point.domain.vo.UpdateMillis;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public class UserPointMapper {

    public static UserPoint toDomain(UserPointModel model) {
        return UserPoint.of(
                new UserPointId(model.id()),
                new Point(model.point()),
                new UpdateMillis(model.updateMillis())
        );
    }

    public static UserPointModel toModel(UserPoint domain) {
        return new UserPointModel(
                domain.userPointId().value(),
                domain.point().value(),
                domain.updateMillis().value()
        );
    }
}

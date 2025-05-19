package io.hhplus.tdd.point.application.port.in;

import io.hhplus.tdd.point.domain.model.UserPoint;

public interface GetUserPointQuery {
    UserPoint getPoint(GetUserPointCommand command);
}

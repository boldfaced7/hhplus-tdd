package io.hhplus.tdd.point.application.port.out;

import io.hhplus.tdd.point.domain.model.UserPoint;

public interface UpdateUserPointPort {
    UserPoint updateUserPoint(UserPoint userPoint);
}

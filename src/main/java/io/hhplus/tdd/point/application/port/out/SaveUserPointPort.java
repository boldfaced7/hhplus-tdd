package io.hhplus.tdd.point.application.port.out;

import io.hhplus.tdd.point.domain.model.UserPoint;

public interface SaveUserPointPort {
    UserPoint saveUserPoint(UserPoint userPoint);
}

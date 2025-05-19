package io.hhplus.tdd.point.application.port.out;

import java.util.Optional;

import io.hhplus.tdd.point.domain.model.UserPoint;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public interface FindUserPointPort {
    Optional<UserPoint> findByUserPointId(UserPointId id);
}

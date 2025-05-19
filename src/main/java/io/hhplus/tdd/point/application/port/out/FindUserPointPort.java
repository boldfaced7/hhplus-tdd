package io.hhplus.tdd.point.application.port.out;

import java.util.Optional;

import io.hhplus.tdd.point.domain.vo.Point;
import io.hhplus.tdd.point.domain.vo.UserPointId;
public interface FindUserPointPort {
    Optional<Point> findByUserPointId(UserPointId id);
}

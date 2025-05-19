package io.hhplus.tdd.point.application.port.out;

import io.hhplus.tdd.point.domain.vo.Point;

public interface SaveUserPointPort {
    Point saveUserPoint(Point point);
}

package io.hhplus.tdd.point.domain.exception;

import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.Point;

public class PointNotEnoughException extends RuntimeException {
    public PointNotEnoughException(Point current, Amount requested) {
        super("Point is not enough. current: " + current.value() + ", requested: " + requested.value());
    }
}

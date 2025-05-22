package io.hhplus.tdd.point.domain.vo;

import io.hhplus.tdd.point.domain.exception.PointNegativeException;
import io.hhplus.tdd.point.domain.exception.PointNotEnoughException;

public record Point(
        Long value
) {
    
    public Point {
        if (value < 0) {
            throw new PointNegativeException(value);
        }
    }

    public static Point zero() {
        return new Point(0L);
    }

    public Point add(Amount amount) {
        return new Point(this.value + amount.value());

    }

    public Point subtract(Amount amount) {
        if (this.value < amount.value()) {
            throw new PointNotEnoughException(this, amount);
        }
        return new Point(this.value - amount.value());
    }
}

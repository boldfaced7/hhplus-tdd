package io.hhplus.tdd.point.domain.exception;

public class PointNegativeException extends RuntimeException {
    public PointNegativeException(Long value) {
        super("Point is negative. value: " + value);
    }
}

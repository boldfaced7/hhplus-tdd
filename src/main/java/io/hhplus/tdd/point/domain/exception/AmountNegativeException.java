package io.hhplus.tdd.point.domain.exception;

public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException(Long value) {
        super("Amount is negative. value: " + value);
    }
}

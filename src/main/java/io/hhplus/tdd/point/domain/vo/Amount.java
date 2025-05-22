package io.hhplus.tdd.point.domain.vo;

import io.hhplus.tdd.point.domain.exception.AmountNegativeException;

public record Amount(
        Long value
) {
    public Amount {
        if (value < 0) {
            throw new AmountNegativeException(value);
        }
    }
}

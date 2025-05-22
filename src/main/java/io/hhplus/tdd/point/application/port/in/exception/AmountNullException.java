package io.hhplus.tdd.point.application.port.in.exception;

public class AmountNullException extends RuntimeException {
    public AmountNullException() {
        super("Amount is null");
    }
}
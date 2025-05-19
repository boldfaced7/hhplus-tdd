package io.hhplus.tdd.point.application.port.in.exception;

public class UserPointIdNullException extends RuntimeException {
    public UserPointIdNullException() {
        super("UserPointId is null");
    }
}
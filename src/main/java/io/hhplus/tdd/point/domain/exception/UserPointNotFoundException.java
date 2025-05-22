package io.hhplus.tdd.point.domain.exception;

import io.hhplus.tdd.point.domain.vo.UserPointId;

public class UserPointNotFoundException extends RuntimeException {
    public UserPointNotFoundException(UserPointId userPointId) {
        super(String.format("UserPoint with id %s not found", userPointId));
    }

}

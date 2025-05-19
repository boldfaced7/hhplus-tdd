package io.hhplus.tdd.point.application.port.in;

import io.hhplus.tdd.point.application.port.in.exception.UserPointIdNullException;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public record GetPointCommand(
        UserPointId userPointId
) {
    public GetPointCommand {
        if (userPointId == null) {
            throw new UserPointIdNullException();
        }
    }
}

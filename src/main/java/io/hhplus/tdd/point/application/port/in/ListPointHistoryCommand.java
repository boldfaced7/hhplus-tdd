package io.hhplus.tdd.point.application.port.in;

import io.hhplus.tdd.point.application.port.in.exception.UserPointIdNullException;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public record ListPointHistoryCommand(
        UserPointId userPointId
) {
    public ListPointHistoryCommand {
        if (userPointId == null) {
            throw new UserPointIdNullException();
        }
    }
}

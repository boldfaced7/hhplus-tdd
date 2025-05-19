package io.hhplus.tdd.point.application.port.in;

import io.hhplus.tdd.point.application.port.in.exception.AmountNullException;
import io.hhplus.tdd.point.application.port.in.exception.UserPointIdNullException;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public record ChargeUserPointCommand(
        UserPointId userPointId,
        Amount amount
) {
    public ChargeUserPointCommand {
        if (userPointId == null) {
            throw new UserPointIdNullException();
        }
        if (amount == null) {
            throw new AmountNullException();
        }
    }
}

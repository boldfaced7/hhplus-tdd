package io.hhplus.tdd.point.domain.event;

import io.hhplus.tdd.point.domain.enums.TransactionType;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public record UserPointUsed(
        UserPointId userPointId,
        Amount amount
) implements UserPointChanged {

    public TransactionType transactionType() {
        return TransactionType.USE;
    }
}

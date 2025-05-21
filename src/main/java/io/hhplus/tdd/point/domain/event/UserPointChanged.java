package io.hhplus.tdd.point.domain.event;

import io.hhplus.tdd.point.domain.enums.TransactionType;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public interface UserPointChanged {
    UserPointId userPointId();
    Amount amount();
    TransactionType transactionType();
}

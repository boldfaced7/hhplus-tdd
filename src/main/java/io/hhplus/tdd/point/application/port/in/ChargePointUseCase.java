package io.hhplus.tdd.point.application.port.in;

import io.hhplus.tdd.point.domain.model.PointHistory;

public interface ChargePointUseCase {
    PointHistory chargePoint(ChargePointCommand command);
}

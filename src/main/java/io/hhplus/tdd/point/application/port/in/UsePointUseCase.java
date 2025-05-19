package io.hhplus.tdd.point.application.port.in;

import io.hhplus.tdd.point.domain.model.PointHistory;

public interface UsePointUseCase {
    PointHistory usePoint(ChargePointCommand command);
}

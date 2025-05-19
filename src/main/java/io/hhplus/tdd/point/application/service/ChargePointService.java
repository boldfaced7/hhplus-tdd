package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.ChargePointCommand;
import io.hhplus.tdd.point.application.port.in.ChargePointUseCase;
import io.hhplus.tdd.point.application.port.out.SavePointHistoryPort;
import io.hhplus.tdd.point.application.port.out.UpdateUserPointPort;
import io.hhplus.tdd.point.domain.model.PointHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargePointService implements ChargePointUseCase {

    private final UpdateUserPointPort saveUserPointPort;
    private final SavePointHistoryPort savePointHistoryPort;

    @Override
    public PointHistory chargePoint(ChargePointCommand command) {
        return null;
    }

}

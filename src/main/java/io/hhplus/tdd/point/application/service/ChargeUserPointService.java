package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.ChargeUserPointCommand;
import io.hhplus.tdd.point.application.port.in.ChargeUserPointUseCase;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.application.port.out.SavePointHistoryPort;
import io.hhplus.tdd.point.application.port.out.UpdateUserPointPort;
import io.hhplus.tdd.point.domain.exception.UserPointNotFoundException;
import io.hhplus.tdd.point.domain.model.PointHistory;
import io.hhplus.tdd.point.domain.model.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargeUserPointService implements ChargeUserPointUseCase {

    private final FindUserPointPort getUserPointPort;
    private final UpdateUserPointPort updateUserPointPort;
    private final SavePointHistoryPort savePointHistoryPort;

    @Override
    public UserPoint chargePoint(ChargeUserPointCommand command) {
        UserPoint found = getUserPointPort.findByUserPointId(command.userPointId())
                .orElseThrow(() -> new UserPointNotFoundException(command.userPointId()));

        UserPoint charged = found.chargePoint(command.amount());
        UserPoint updated = updateUserPointPort.updatePoint(charged);

        savePointHistoryPort.savePointHistory(PointHistory.charge(
                command.userPointId(), 
                command.amount()
        ));
        return updated;
    }

}

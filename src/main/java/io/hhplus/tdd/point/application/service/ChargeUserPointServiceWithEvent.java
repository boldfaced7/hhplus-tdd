package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.ChargeUserPointCommand;
import io.hhplus.tdd.point.application.port.in.ChargeUserPointUseCase;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.application.port.out.PublishUserPointChangedPort;
import io.hhplus.tdd.point.application.port.out.UpdateUserPointPort;
import io.hhplus.tdd.point.domain.event.UserPointChanged;
import io.hhplus.tdd.point.domain.exception.UserPointNotFoundException;
import io.hhplus.tdd.point.domain.model.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargeUserPointServiceWithEvent implements ChargeUserPointUseCase {

    private final FindUserPointPort getUserPointPort;
    private final UpdateUserPointPort updateUserPointPort;
    private final PublishUserPointChangedPort publishUserPointChangedPort;

    @Override
    public UserPoint chargePoint(ChargeUserPointCommand command) {
        UserPoint found = getUserPointPort.findByUserPointId(command.userPointId())
                .orElseThrow(() -> new UserPointNotFoundException(command.userPointId()));

        UserPoint charged = found.chargePoint(command.amount());
        List<UserPointChanged> raised = charged.pullEvents();

        publishUserPointChangedPort.publish(raised);
        return updateUserPointPort.updateUserPoint(charged);
    }
}

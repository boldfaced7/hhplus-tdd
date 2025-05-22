package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.ChargeUserPointCommand;
import io.hhplus.tdd.point.application.port.in.ChargeUserPointUseCase;
import io.hhplus.tdd.point.application.port.out.ExecuteLockPort;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.application.port.out.PublishUserPointChangedPort;
import io.hhplus.tdd.point.application.port.out.UpdateUserPointPort;
import io.hhplus.tdd.point.domain.event.UserPointChanged;
import io.hhplus.tdd.point.domain.exception.UserPointNotFoundException;
import io.hhplus.tdd.point.domain.model.UserPoint;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChargeUserPointServiceWithEvent implements ChargeUserPointUseCase {

    private static final String KEY_PREFIX = "CHARGE_USER_POINT::";

    private final FindUserPointPort getUserPointPort;
    private final UpdateUserPointPort updateUserPointPort;
    private final PublishUserPointChangedPort publishUserPointChangedPort;
    private final ExecuteLockPort executeLockPort;

    @Override
    public UserPoint chargePoint(ChargeUserPointCommand command) {
        UserPointId userPointId = command.userPointId();
        executeLockPort.lock(KEY_PREFIX + userPointId);
        try {
            UserPoint found = getUserPointPort.findByUserPointId(userPointId)
                    .orElseThrow(() -> new UserPointNotFoundException(userPointId));

            UserPoint charged = found.chargePoint(command.amount());
            List<UserPointChanged> raised = charged.pullEvents();

            publishUserPointChangedPort.publish(raised);
            return updateUserPointPort.updateUserPoint(charged);

        } finally {
            executeLockPort.unlock(KEY_PREFIX + userPointId);
        }
    }
}

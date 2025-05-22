package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.UseUserPointCommand;
import io.hhplus.tdd.point.application.port.in.UseUserPointUseCase;
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
public class UseUserPointServiceWithEvent implements UseUserPointUseCase {

    private static final String KEY_PREFIX = "USE_USER_POINT::";

    private final FindUserPointPort findUserPointPort;
    private final UpdateUserPointPort updateUserPointPort;
    private final PublishUserPointChangedPort publishUserPointChangedPort;
    private final ExecuteLockPort executeLockPort;

    @Override
    public UserPoint usePoint(UseUserPointCommand command) {
        UserPointId userPointId = command.userPointId();
        executeLockPort.lock(KEY_PREFIX + userPointId);
        try {
            UserPoint found = findUserPointPort.findByUserPointId(userPointId)
                    .orElseThrow(() -> new UserPointNotFoundException(userPointId));

            UserPoint used = found.usePoint(command.amount());
            List<UserPointChanged> raised = used.pullEvents();

            publishUserPointChangedPort.publish(raised);
            return updateUserPointPort.updateUserPoint(used);
        
        } finally {
            executeLockPort.unlock(KEY_PREFIX + userPointId);
        }
    }
}

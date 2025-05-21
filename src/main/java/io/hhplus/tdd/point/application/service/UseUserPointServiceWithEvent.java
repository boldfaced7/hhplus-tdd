package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.UseUserPointCommand;
import io.hhplus.tdd.point.application.port.in.UseUserPointUseCase;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.application.port.out.PublishUserPointChangedPort;
import io.hhplus.tdd.point.application.port.out.UpdateUserPointPort;
import io.hhplus.tdd.point.domain.event.UserPointChanged;
import io.hhplus.tdd.point.domain.exception.UserPointNotFoundException;
import io.hhplus.tdd.point.domain.model.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class UseUserPointServiceWithEvent implements UseUserPointUseCase {

    private final FindUserPointPort findUserPointPort;
    private final UpdateUserPointPort updateUserPointPort;
    private final PublishUserPointChangedPort publishUserPointChangedPort;
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public UserPoint usePoint(UseUserPointCommand command) {
        lock.lock();
        try {
            UserPoint found = findUserPointPort.findByUserPointId(command.userPointId())
                    .orElseThrow(() -> new UserPointNotFoundException(command.userPointId()));

            UserPoint used = found.usePoint(command.amount());
            List<UserPointChanged> raised = used.pullEvents();

            publishUserPointChangedPort.publish(raised);
            return updateUserPointPort.updateUserPoint(used);
        } finally {
            lock.unlock();
        }
    }
}

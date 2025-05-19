package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.UseUserPointCommand;
import io.hhplus.tdd.point.application.port.in.UseUserPointUseCase;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.application.port.out.SavePointHistoryPort;
import io.hhplus.tdd.point.application.port.out.UpdateUserPointPort;
import io.hhplus.tdd.point.domain.model.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UseUserPointService implements UseUserPointUseCase {

    private final FindUserPointPort findUserPointPort;
    private final UpdateUserPointPort updateUserPointPort;
    private final SavePointHistoryPort savePointHistoryPort;

    @Override
    public UserPoint usePoint(UseUserPointCommand command) {
        return null;
    }
}

package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.GetUserPointCommand;
import io.hhplus.tdd.point.application.port.in.GetUserPointQuery;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.domain.model.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserPointService implements GetUserPointQuery {

    private final FindUserPointPort findUserPointPort;

    @Override
    public UserPoint getPoint(GetUserPointCommand command) {
        return null;
    }
}

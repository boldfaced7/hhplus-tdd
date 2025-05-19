package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.GetPointCommand;
import io.hhplus.tdd.point.application.port.in.GetPointQuery;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.domain.model.PointHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPointService implements GetPointQuery {

    private final FindUserPointPort findUserPointPort;

    @Override
    public PointHistory getPoint(GetPointCommand command) {
        return null;
    }
}

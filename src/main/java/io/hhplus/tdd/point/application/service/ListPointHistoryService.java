package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.ListPointHistoryCommand;
import io.hhplus.tdd.point.application.port.in.ListPointHistoryQuery;
import io.hhplus.tdd.point.application.port.out.ListPointHistoryPort;
import io.hhplus.tdd.point.domain.model.PointHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListPointHistoryService implements ListPointHistoryQuery {

    private final ListPointHistoryPort listPointHistoryPort;

    @Override
    public List<PointHistory> listPointHistory(ListPointHistoryCommand command) {
        return listPointHistoryPort.listByUserPointId(command.userPointId());
    }
}

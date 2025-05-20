package io.hhplus.tdd.point.adapter.out.persistence;

import io.hhplus.tdd.point.application.port.out.ListPointHistoryPort;
import io.hhplus.tdd.point.application.port.out.SavePointHistoryPort;
import io.hhplus.tdd.point.domain.model.PointHistory;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PointHistoryPersistenceAdapter implements
        ListPointHistoryPort,
        SavePointHistoryPort
{
    private final PointHistoryTable pointHistoryTable = new PointHistoryTable();

    @Override
    public List<PointHistory> listByUserPointId(UserPointId userId) {
        return pointHistoryTable.selectAllByUserId(userId.value())
                .stream()
                .map(PointHistoryMapper::toDomain)
                .toList();
    }

    @Override
    public PointHistory savePointHistory(PointHistory pointHistory) {
        PointHistoryModel saved = pointHistoryTable.insert(
                pointHistory.userPointId().value(),
                pointHistory.amount().value(),
                pointHistory.transactionType(),
                pointHistory.updateMillis().value()
        );
        return PointHistoryMapper.toDomain(saved);
    }
}

package io.hhplus.tdd.point.adapter.out.persistence;

import io.hhplus.tdd.point.domain.model.PointHistory;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.PointHistoryId;
import io.hhplus.tdd.point.domain.vo.UpdateMillis;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public class PointHistoryMapper {

    public static PointHistory toDomain(PointHistoryModel model) {
        return PointHistory.of(
                new PointHistoryId(model.id()),
                new UserPointId(model.userId()),
                new Amount(model.amount()),
                model.type(),
                new UpdateMillis(model.updateMillis())
        );
    }

    public static PointHistoryModel toModel(PointHistory domain) {
        return new PointHistoryModel(
                domain.pointHistoryId().value(),
                domain.userPointId().value(),
                domain.amount().value(),
                domain.transactionType(),
                domain.updateMillis().value()
        );
    }
}

package io.hhplus.tdd.point.application.port.out;

import java.util.List;

import io.hhplus.tdd.point.domain.model.PointHistory;
import io.hhplus.tdd.point.domain.vo.UserPointId;

public interface ListPointHistoryPort {
    List<PointHistory> listByUserPointId(UserPointId userId);
}

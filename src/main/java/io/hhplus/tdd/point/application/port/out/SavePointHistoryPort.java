package io.hhplus.tdd.point.application.port.out;

import io.hhplus.tdd.point.domain.model.PointHistory;

public interface SavePointHistoryPort {
    PointHistory savePointHistory(PointHistory pointHistory);
}

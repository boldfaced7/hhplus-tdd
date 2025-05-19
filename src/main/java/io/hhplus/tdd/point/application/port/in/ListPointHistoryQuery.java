package io.hhplus.tdd.point.application.port.in;

import io.hhplus.tdd.point.domain.model.PointHistory;

import java.util.List;

public interface ListPointHistoryQuery {
    List<PointHistory> listPointHistory(ListPointHistoryCommand command);
}

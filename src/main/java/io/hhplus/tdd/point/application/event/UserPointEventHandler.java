package io.hhplus.tdd.point.application.event;

import io.hhplus.tdd.point.application.port.out.SavePointHistoryPort;
import io.hhplus.tdd.point.domain.event.UserPointChanged;
import io.hhplus.tdd.point.domain.event.UserPointUsed;
import io.hhplus.tdd.point.domain.model.PointHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPointEventHandler {

    private final SavePointHistoryPort savePointHistoryPort;

    @Async
    @EventListener
    public void handle(UserPointChanged userPointChanged) {
        PointHistory toBeSaved = PointHistory.charge(
                userPointChanged.userPointId(),
                userPointChanged.amount()
        );
        savePointHistoryPort.savePointHistory(toBeSaved);
    }

    @Async
    @EventListener
    public void handle(UserPointUsed userPointUsed) {
        PointHistory toBeSaved = PointHistory.use(
                userPointUsed.userPointId(),
                userPointUsed.amount()
        );
        savePointHistoryPort.savePointHistory(toBeSaved);
    }
}

package io.hhplus.tdd.point.application.event;

import io.hhplus.tdd.point.application.port.out.SavePointHistoryPort;
import io.hhplus.tdd.point.domain.event.UserPointCharged;
import io.hhplus.tdd.point.domain.event.UserPointUsed;
import io.hhplus.tdd.point.domain.model.PointHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPointEventHandler {

    private final SavePointHistoryPort savePointHistoryPort;

    @Async
    @EventListener
    public void handle(UserPointCharged userPointCharged) {
        PointHistory toBeSaved = PointHistory.charge(
                userPointCharged.userPointId(),
                userPointCharged.amount()
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

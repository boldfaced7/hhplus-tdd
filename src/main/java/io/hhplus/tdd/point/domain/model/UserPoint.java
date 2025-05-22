package io.hhplus.tdd.point.domain.model;

import io.hhplus.tdd.point.domain.event.UserPointChanged;
import io.hhplus.tdd.point.domain.event.UserPointCharged;
import io.hhplus.tdd.point.domain.event.UserPointUsed;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.Point;
import io.hhplus.tdd.point.domain.vo.UpdateMillis;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

public record UserPoint(
        UserPointId userPointId,
        Point point,
        UpdateMillis updateMillis,
        List<UserPointChanged> userPointChangedList
) {

    @Builder
    public UserPoint(
            UserPointId userPointId,
            Point point,
            UpdateMillis updateMillis
    ) {
        this(
                userPointId,
                point,
                updateMillis,
                new ArrayList<>()
        );
    }

    public static UserPoint of(
            Point point
    ) {
        return UserPoint.builder()
                .point(point)
                .build();
    }

    public static UserPoint of(
            UserPointId userPointId,
            Point point,
            UpdateMillis updateMillis
    ) {
        return UserPoint.builder()
                .userPointId(userPointId)
                .point(point)
                .updateMillis(updateMillis)
                .build();
    }

    public List<UserPointChanged> userPointChangedList() {
        return List.copyOf(userPointChangedList);
    }

    public List<UserPointChanged> pullEvents() {
        var events = List.copyOf(userPointChangedList);
        userPointChangedList.clear();

        return events;
    }

    public UserPoint chargePoint(Amount amount) {
        var charged = new UserPoint(
                userPointId,
                point.add(amount),
                updateMillis,
                userPointChangedList
        );
        var raised = new UserPointCharged(
                charged.userPointId,
                amount
        );
        charged.userPointChangedList.add(raised);

        return charged;
    }

    public UserPoint usePoint(Amount amount) {
        var used = new UserPoint(
                userPointId,
                point.subtract(amount),
                updateMillis,
                userPointChangedList
        );
        var raised = new UserPointUsed(
                used.userPointId,
                amount
        );
        used.userPointChangedList.add(raised);

        return used;
    }

}

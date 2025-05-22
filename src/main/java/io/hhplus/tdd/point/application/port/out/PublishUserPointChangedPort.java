package io.hhplus.tdd.point.application.port.out;

import io.hhplus.tdd.point.domain.event.UserPointChanged;

import java.util.List;

public interface PublishUserPointChangedPort {
    void publish(List<UserPointChanged> events);
}

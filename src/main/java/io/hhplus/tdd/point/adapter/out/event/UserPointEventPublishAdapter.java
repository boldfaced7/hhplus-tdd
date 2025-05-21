package io.hhplus.tdd.point.adapter.out.event;

import io.hhplus.tdd.point.application.port.out.PublishUserPointChangedPort;
import io.hhplus.tdd.point.domain.event.UserPointChanged;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserPointEventPublishAdapter implements
        PublishUserPointChangedPort
{
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(List<UserPointChanged> events) {
        events.forEach(applicationEventPublisher::publishEvent);
    }
}

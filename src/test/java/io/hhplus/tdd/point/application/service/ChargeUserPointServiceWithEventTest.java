package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.ChargeUserPointCommand;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.application.port.out.PublishUserPointChangedPort;
import io.hhplus.tdd.point.application.port.out.UpdateUserPointPort;
import io.hhplus.tdd.point.domain.event.UserPointChanged;
import io.hhplus.tdd.point.domain.exception.UserPointNotFoundException;
import io.hhplus.tdd.point.domain.model.UserPoint;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.Point;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChargeUserPointServiceWithEventTest {

    @Mock
    private FindUserPointPort findUserPointPort;

    @Mock
    private UpdateUserPointPort updateUserPointPort;

    @Mock
    private PublishUserPointChangedPort publishUserPointChangedPort;

    private ChargeUserPointServiceWithEvent chargeUserPointService;

    @BeforeEach
    void setUp() {
        chargeUserPointService = new ChargeUserPointServiceWithEvent(
                findUserPointPort, updateUserPointPort, publishUserPointChangedPort);
    }

    @Test
    @DisplayName("포인트를 충전하고 이벤트를 발행할 수 있다")
    void chargePoint() {
        // given
        var userPointId = new UserPointId(1L);
        var amount = new Amount(1000L);
        var command = new ChargeUserPointCommand(userPointId, amount);
        var expectedUserPoint = UserPoint.of(new Point(1000L));
        
        when(findUserPointPort.findByUserPointId(any())).thenReturn(Optional.of(expectedUserPoint));
        when(updateUserPointPort.updateUserPoint(any())).thenReturn(expectedUserPoint);

        // when
        var result = chargeUserPointService.chargePoint(command);

        // then
        assertThat(result).isNotNull();
        verify(findUserPointPort).findByUserPointId(any());
        verify(updateUserPointPort).updateUserPoint(any());
        verify(publishUserPointChangedPort).publish(any());
    }

    @Test
    @DisplayName("사용자 포인트를 찾을 수 없으면 예외가 발생한다")
    void chargePoint_UserPointNotFound() {
        // given
        var userPointId = new UserPointId(1L);
        var amount = new Amount(1000L);
        var command = new ChargeUserPointCommand(userPointId, amount);
        
        when(findUserPointPort.findByUserPointId(any())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> chargeUserPointService.chargePoint(command))
            .isInstanceOf(UserPointNotFoundException.class);
    }
}
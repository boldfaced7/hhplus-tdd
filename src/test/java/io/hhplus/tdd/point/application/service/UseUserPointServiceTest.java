package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.UseUserPointCommand;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.application.port.out.SavePointHistoryPort;
import io.hhplus.tdd.point.application.port.out.UpdateUserPointPort;
import io.hhplus.tdd.point.domain.exception.PointNotEnoughException;
import io.hhplus.tdd.point.domain.exception.UserPointNotFoundException;
import io.hhplus.tdd.point.domain.model.PointHistory;
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
class UseUserPointServiceTest {

    @Mock
    private FindUserPointPort findUserPointPort;

    @Mock
    private UpdateUserPointPort updateUserPointPort;

    @Mock
    private SavePointHistoryPort savePointHistoryPort;

    private UseUserPointService usePointService;

    @BeforeEach
    void setUp() {
        usePointService = new UseUserPointService(findUserPointPort, updateUserPointPort, savePointHistoryPort);
    }

    @Test
    @DisplayName("포인트를 사용할 수 있다")
    void usePoint() {
        // given
        var userPointId = new UserPointId(1L);
        var amount = new Amount(500L);
        var command = new UseUserPointCommand(userPointId, amount);
        var expectedHistory = PointHistory.use(userPointId, amount);
        var expectedUserPoint = UserPoint.of(new Point(500L));
        
        when(findUserPointPort.findByUserPointId(any())).thenReturn(Optional.of(expectedUserPoint));
        when(savePointHistoryPort.savePointHistory(any())).thenReturn(expectedHistory);
        when(updateUserPointPort.updatePoint(any())).thenReturn(expectedUserPoint);

        // when
        var result = usePointService.usePoint(command);

        // then
        assertThat(result).isNotNull();
        verify(findUserPointPort).findByUserPointId(any());
        verify(updateUserPointPort).updatePoint(any());
        verify(savePointHistoryPort).savePointHistory(any());
    }

    @Test
    @DisplayName("포인트 잔고가 부족하면 포인트 사용에 실패한다")
    void usePoint_NotEnoughPoint() {
        // given
        var userPointId = new UserPointId(1L);
        var amount = new Amount(1000L);
        var command = new UseUserPointCommand(userPointId, amount);
        var currentUserPoint = UserPoint.of(new Point(500L));
        
        when(findUserPointPort.findByUserPointId(any())).thenReturn(Optional.of(currentUserPoint));

        // when & then
        assertThatThrownBy(() -> usePointService.usePoint(command))
            .isInstanceOf(PointNotEnoughException.class);
    }

    @Test
    @DisplayName("사용자 포인트를 찾을 수 없으면 예외가 발생한다")
    void usePoint_UserPointNotFound() {
        // given
        var userPointId = new UserPointId(1L);
        var amount = new Amount(500L);
        var command = new UseUserPointCommand(userPointId, amount);
        
        when(findUserPointPort.findByUserPointId(any())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> usePointService.usePoint(command))
            .isInstanceOf(UserPointNotFoundException.class);
    }
} 
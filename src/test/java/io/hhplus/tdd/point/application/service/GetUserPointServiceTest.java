package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.GetUserPointCommand;
import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.domain.exception.UserPointNotFoundException;
import io.hhplus.tdd.point.domain.model.UserPoint;
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
class GetUserPointServiceTest {

    @Mock
    private FindUserPointPort findUserPointPort;

    private GetUserPointService getPointService;

    @BeforeEach
    void setUp() {
        getPointService = new GetUserPointService(findUserPointPort);
    }

    @Test
    @DisplayName("사용자의 포인트를 조회할 수 있다")
    void getPoint() {
        // given
        var userPointId = new UserPointId(1L);
        var command = new GetUserPointCommand(userPointId);
        var expectedUserPoint = UserPoint.of(new Point(1000L));
        
        when(findUserPointPort.findByUserPointId(any())).thenReturn(Optional.of(expectedUserPoint));

        // when
        var result = getPointService.getPoint(command);

        // then
        assertThat(result).isNotNull();
        verify(findUserPointPort).findByUserPointId(any());
    }

    @Test
    @DisplayName("사용자 포인트를 찾을 수 없으면 예외가 발생한다")
    void getPoint_UserPointNotFound() {
        // given
        var userPointId = new UserPointId(1L);
        var command = new GetUserPointCommand(userPointId);
        
        when(findUserPointPort.findByUserPointId(any())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> getPointService.getPoint(command))
            .isInstanceOf(UserPointNotFoundException.class);
    }
} 
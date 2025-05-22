package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.ListPointHistoryCommand;
import io.hhplus.tdd.point.application.port.out.ListPointHistoryPort;
import io.hhplus.tdd.point.domain.model.PointHistory;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListPointHistoryServiceTest {

    @Mock
    private ListPointHistoryPort listPointHistoryPort;

    private ListPointHistoryService listPointHistoryService;

    @BeforeEach
    void setUp() {
        listPointHistoryService = new ListPointHistoryService(listPointHistoryPort);
    }

    @Test
    @DisplayName("사용자의 포인트 충전/사용 내역을 조회할 수 있다")
    void listPointHistory() {
        // given
        var userId = new UserPointId(1L);
        var command = new ListPointHistoryCommand(userId);
        var expectedHistories = List.of(
            PointHistory.charge(userId, new Amount(1000L)),
            PointHistory.use(userId, new Amount(500L))
        );
        
        when(listPointHistoryPort.listByUserPointId(any())).thenReturn(expectedHistories);

        // when
        var result = listPointHistoryService.listPointHistory(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(listPointHistoryPort).listByUserPointId(any());
    }
} 
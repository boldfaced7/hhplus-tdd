package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.*;
import io.hhplus.tdd.point.domain.enums.TransactionType;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ChargeUserPointServiceWithEventConcurrencyTest {

    @Autowired
    private ChargeUserPointServiceWithEvent chargeUserPointService;

    @Autowired
    private GetUserPointQuery getUserPointQuery;

    @Autowired
    private ListPointHistoryQuery listPointHistoryQuery;

    @Test
    @DisplayName("여러 스레드에서 동시에 포인트를 충전해도 정상적으로 처리된다")
    void chargePoint_Concurrent() throws InterruptedException {
        // given
        var userPointId = new UserPointId(1L);
        var threadCount = 10;
        var chargeAmount = 1000L;
        var expectedTotalPoint = threadCount * chargeAmount;

        var executorService = Executors.newFixedThreadPool(threadCount);
        var latch = new CountDownLatch(threadCount);
        var successCount = new AtomicLong(0);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    var command = new ChargeUserPointCommand(userPointId, new Amount(chargeAmount));
                    chargeUserPointService.chargePoint(command);
                    successCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // then
        // 모든 스레드가 정상적으로 실행되었는지 확인
        assertThat(successCount.get()).isEqualTo(threadCount);
        
        // 포인트 충전이 정상적으로 되었는지 확인
        var finalPoint = getUserPointQuery.getPoint(new GetUserPointCommand(userPointId));
        assertThat(finalPoint.point().value()).isEqualTo(expectedTotalPoint);

        // 이벤트 핸들러를 통한 PointHistory 생성이 정상적으로 되었는지 확인
        Thread.sleep(1000); // 비동기 이벤트 처리 대기
        var histories = listPointHistoryQuery.listPointHistory(new ListPointHistoryCommand(userPointId));
        assertThat(histories).hasSize(threadCount);
        assertThat(histories).allMatch(history -> 
            history.amount().value() == chargeAmount && 
            history.transactionType() == TransactionType.CHARGE
        );
    }
} 
package io.hhplus.tdd.point.application.service;

import io.hhplus.tdd.point.application.port.in.*;
import io.hhplus.tdd.point.domain.enums.TransactionType;
import io.hhplus.tdd.point.domain.exception.PointNotEnoughException;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UseUserPointServiceWithEventConcurrencyTest {

    @Autowired
    private UseUserPointServiceWithEvent usePointService;

    @Autowired
    private ChargeUserPointServiceWithEvent chargeUserPointService;

    @Autowired
    private GetUserPointQuery getUserPointQuery;

    @Autowired
    private ListPointHistoryQuery listPointHistoryQuery;

    @Test
    @DisplayName("여러 스레드에서 동시에 포인트를 사용할 때 잔고가 정확하게 차감된다")
    void usePoint_Concurrent() throws InterruptedException {
        // given
        var userPointId = new UserPointId(1L);
        var initialPoint = 1000L;
        var useAmount = 200L;
        var threadCount = 10;
        var expectedSuccessCount = 5; // 1000 포인트로 200 포인트씩 5번 사용 가능
        
        // 초기 포인트 충전
        chargeUserPointService.chargePoint(new ChargeUserPointCommand(userPointId, new Amount(initialPoint)));

        var executorService = Executors.newFixedThreadPool(threadCount);
        var latch = new CountDownLatch(threadCount);
        var successCount = new AtomicLong(0);
        var notEnoughCount = new AtomicLong(0);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    var command = new UseUserPointCommand(userPointId, new Amount(useAmount));
                    usePointService.usePoint(command);
                    successCount.incrementAndGet();
                } catch (PointNotEnoughException e) {
                    notEnoughCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        // then
        // 1. 모든 스레드가 정상적으로 실행되었는지 확인
        assertThat(successCount.get() + notEnoughCount.get()).isEqualTo(threadCount);
        
        // 2. 성공한 요청의 수가 예상과 일치하는지 확인
        assertThat(successCount.get()).isEqualTo(expectedSuccessCount);
        
        // 3. 최종 포인트 잔액이 정확한지 확인
        var finalPoint = getUserPointQuery.getPoint(new GetUserPointCommand(userPointId));
        var expectedFinalPoint = initialPoint - (successCount.get() * useAmount);
        assertThat(finalPoint.point().value()).isEqualTo(expectedFinalPoint);

        // 4. PointHistory가 정확하게 기록되었는지 확인
        Thread.sleep(1000); // 비동기 이벤트 처리 대기
        var histories = listPointHistoryQuery.listPointHistory(new ListPointHistoryCommand(userPointId));
        assertThat(histories).hasSize((int)successCount.get() + 1); // 충전 1건 + 사용 성공 건수
        
        // 충전 내역 확인
        var chargeHistory = histories.stream()
            .filter(h -> h.transactionType() == TransactionType.CHARGE)
            .findFirst()
            .orElseThrow();
        assertThat(chargeHistory.amount().value()).isEqualTo(initialPoint);
        
        // 사용 내역 확인
        var useHistories = histories.stream()
            .filter(h -> h.transactionType() == TransactionType.USE)
            .toList();
        assertThat(useHistories).hasSize((int)successCount.get());
        assertThat(useHistories).allMatch(h -> h.amount().value() == useAmount);
    }
} 
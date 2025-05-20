package io.hhplus.tdd.point.adapter.in.web;

import io.hhplus.tdd.point.application.port.in.ListPointHistoryCommand;
import io.hhplus.tdd.point.application.port.in.ListPointHistoryQuery;
import io.hhplus.tdd.point.domain.enums.TransactionType;
import io.hhplus.tdd.point.domain.model.PointHistory;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class ListPointHistoryController {

    private final ListPointHistoryQuery listPointHistoryUseCase;

    @GetMapping("{id}/histories")
    public ResponseEntity<List<Response>> listPointHistory(
            @PathVariable long id
    ) {
        var command = toCommand(id);
        var listed = listPointHistoryUseCase.listPointHistory(command);
        var response = toResponse(listed);

        return ResponseEntity.ok(response);
    }

    private ListPointHistoryCommand toCommand(long id) {
        return new ListPointHistoryCommand(
                new UserPointId(id)
        );
    }

    private List<Response> toResponse(List<PointHistory> pointHistories) {
        return pointHistories.stream()
                .map(ph -> new Response(
                        ph.pointHistoryId().value(),
                        ph.userPointId().value(),
                        ph.amount().value(),
                        ph.transactionType(),
                        ph.updateMillis().value()
                ))
                .toList();
    }

    public record Response(
            long id,
            long userId,
            long amount,
            TransactionType type,
            long updateMillis
    ) {}
}

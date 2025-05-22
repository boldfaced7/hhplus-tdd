package io.hhplus.tdd.point.adapter.in.web;

import io.hhplus.tdd.point.application.port.in.ChargeUserPointCommand;
import io.hhplus.tdd.point.application.port.in.ChargeUserPointUseCase;
import io.hhplus.tdd.point.domain.model.UserPoint;
import io.hhplus.tdd.point.domain.vo.Amount;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class ChargeUserPointController {

    private final ChargeUserPointUseCase chargeUserPointUseCase;

    @PatchMapping("{id}/charge")
    public ResponseEntity<Response> chargeUserPoint(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        var command = toCommand(id, amount);
        var charged = chargeUserPointUseCase.chargePoint(command);
        var response = toResponse(charged);

        return ResponseEntity.ok(response);
    }

    ChargeUserPointCommand toCommand(long id, long amount) {
        return new ChargeUserPointCommand(
                new UserPointId(id),
                new Amount(amount)
        );
    }
    Response toResponse(UserPoint userPoint) {
        return new Response(
                userPoint.userPointId().value(),
                userPoint.point().value(),
                userPoint.updateMillis().value()
        );
    }

    public record Response(
            long id,
            long point,
            long updateMillis
    ) {}
}
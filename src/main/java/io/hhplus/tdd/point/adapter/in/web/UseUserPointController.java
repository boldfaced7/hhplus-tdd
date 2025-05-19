package io.hhplus.tdd.point.adapter.in.web;

import io.hhplus.tdd.point.application.port.in.UseUserPointCommand;
import io.hhplus.tdd.point.application.port.in.UseUserPointUseCase;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import io.hhplus.tdd.point.domain.model.UserPoint;
import io.hhplus.tdd.point.domain.vo.Amount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class UseUserPointController {

    private final UseUserPointUseCase useUserPointUseCase;

    @PatchMapping("{id}/use")
    public ResponseEntity<Response> useUserPoint(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        var command = toCommand(id, amount);
        var used = useUserPointUseCase.usePoint(command);
        var response = toResponse(used);

        return ResponseEntity.ok(response);
    }

    private UseUserPointCommand toCommand(long id, long amount) {
        return new UseUserPointCommand(
                new UserPointId(id), 
                new Amount(amount)
        );
    }

    private Response toResponse(UserPoint userPoint) {
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

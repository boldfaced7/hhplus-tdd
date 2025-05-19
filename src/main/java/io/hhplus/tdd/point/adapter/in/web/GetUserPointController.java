package io.hhplus.tdd.point.adapter.in.web;

import io.hhplus.tdd.point.application.port.in.GetUserPointCommand;
import io.hhplus.tdd.point.application.port.in.GetUserPointQuery;
import io.hhplus.tdd.point.domain.model.UserPoint;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/point")
@RequiredArgsConstructor
public class GetUserPointController {

    private final GetUserPointQuery getUserPointUseCase;

    @GetMapping("{id}")
    public ResponseEntity<Response> getUserPoint(
            @PathVariable long id
    ) {
        var command = toCommand(id);
        var found = getUserPointUseCase.getPoint(command);
        var response = toResponse(found);

        return ResponseEntity.ok(response);
    }

    GetUserPointCommand toCommand(long id) {
        return new GetUserPointCommand(
                new UserPointId(id)
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

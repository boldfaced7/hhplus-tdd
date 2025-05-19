package io.hhplus.tdd.point.adapter.in.web;

import io.hhplus.tdd.point.adapter.out.persistence.PointHistoryModel;
import io.hhplus.tdd.point.adapter.out.persistence.UserPointModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/point")
public class PointController {

    private static final Logger log = LoggerFactory.getLogger(PointController.class);

    /**
     * TODO - 특정 유저의 포인트를 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}")
    public UserPointModel point(
            @PathVariable long id
    ) {
        return new UserPointModel(0, 0, 0);
    }

    /**
     * TODO - 특정 유저의 포인트 충전/이용 내역을 조회하는 기능을 작성해주세요.
     */
    @GetMapping("{id}/histories")
    public List<PointHistoryModel> history(
            @PathVariable long id
    ) {
        return List.of();
    }

    /**
     * TODO - 특정 유저의 포인트를 충전하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/charge")
    public UserPointModel charge(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        return new UserPointModel(0, 0, 0);
    }

    /**
     * TODO - 특정 유저의 포인트를 사용하는 기능을 작성해주세요.
     */
    @PatchMapping("{id}/use")
    public UserPointModel use(
            @PathVariable long id,
            @RequestBody long amount
    ) {
        return new UserPointModel(0, 0, 0);
    }
}

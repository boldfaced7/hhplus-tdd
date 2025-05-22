package io.hhplus.tdd.point.adapter.out.persistence;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 해당 Table 클래스는 변경하지 않고 공개된 API 만을 사용해 데이터를 제어합니다.
 */
@Component
public class UserPointTable {

    private final Map<Long, UserPointModel> table = new HashMap<>();

    public UserPointModel selectById(Long id) {
        throttle(200);
        return table.getOrDefault(id, UserPointModel.empty(id));
    }

    public UserPointModel insertOrUpdate(long id, long amount) {
        throttle(300);
        UserPointModel userPointModel = new UserPointModel(id, amount, System.currentTimeMillis());
        table.put(id, userPointModel);
        return userPointModel;
    }

    private void throttle(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * millis));
        } catch (InterruptedException ignored) {

        }
    }
}

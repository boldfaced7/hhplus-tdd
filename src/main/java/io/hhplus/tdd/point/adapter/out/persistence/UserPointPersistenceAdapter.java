package io.hhplus.tdd.point.adapter.out.persistence;

import io.hhplus.tdd.point.application.port.out.FindUserPointPort;
import io.hhplus.tdd.point.application.port.out.SaveUserPointPort;
import io.hhplus.tdd.point.application.port.out.UpdateUserPointPort;
import io.hhplus.tdd.point.domain.model.UserPoint;
import io.hhplus.tdd.point.domain.vo.UserPointId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPointPersistenceAdapter implements
        FindUserPointPort,
        SaveUserPointPort,
        UpdateUserPointPort
{

    private final UserPointTable userPointTable = new UserPointTable();

    @Override
    public Optional<UserPoint> findByUserPointId(UserPointId id) {
        UserPointModel found = userPointTable.selectById(id.value());
        UserPoint converted = UserPointMapper.toDomain(found);

        return Optional.of(converted);
    }

    @Override
    public UserPoint saveUserPoint(UserPoint userPoint) {
        UserPointModel saved = userPointTable.insertOrUpdate(
                userPoint.userPointId().value(),
                userPoint.point().value()
        );
        return UserPointMapper.toDomain(saved);
    }

    @Override
    public UserPoint updateUserPoint(UserPoint userPoint) {
        UserPointModel updated = userPointTable.insertOrUpdate(
                userPoint.userPointId().value(),
                userPoint.point().value()
        );
        return UserPointMapper.toDomain(updated);
    }
}

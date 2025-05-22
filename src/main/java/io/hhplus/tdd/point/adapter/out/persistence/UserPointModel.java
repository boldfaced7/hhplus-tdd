package io.hhplus.tdd.point.adapter.out.persistence;

public record UserPointModel(
        long id,
        long point,
        long updateMillis
) {

    public static UserPointModel empty(long id) {
        return new UserPointModel(id, 0, System.currentTimeMillis());
    }
}

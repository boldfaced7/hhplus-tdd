package io.hhplus.tdd.point.application.port.out;

public interface ExecuteLockPort {
    void lock(String key);
    void unlock(String key);
}
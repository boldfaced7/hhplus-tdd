package io.hhplus.tdd.point.adapter.out.concurrency;

import io.hhplus.tdd.point.application.port.out.ExecuteLockPort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class LocalLockAdapter implements ExecuteLockPort {

    private final Map<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();


    @Override
    public void lock(String key) {
        lockMap.computeIfAbsent(key, k -> new ReentrantLock()).lock();
    }

    @Override
    public void unlock(String key) {
        if (!lockMap.containsKey(key)) {
            return;
        }
        lockMap.get(key).unlock();
    }
}

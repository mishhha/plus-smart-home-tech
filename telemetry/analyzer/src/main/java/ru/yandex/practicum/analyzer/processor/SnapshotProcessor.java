package ru.yandex.practicum.analyzer.processor;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnapshotProcessor {

    public void start() {
        log.info("SnapshotProcessor запущен в потоке: {}", Thread.currentThread().getName());
    }

    @PreDestroy
    public void stop() {
        log.info("SnapshotProcessor: остановка");
    }

}
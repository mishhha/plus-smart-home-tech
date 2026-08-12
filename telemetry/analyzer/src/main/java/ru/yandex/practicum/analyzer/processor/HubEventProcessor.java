package ru.yandex.practicum.analyzer.processor;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubEventProcessor implements Runnable {

    @Override
    public void run() {
        log.info("HubEventProcessor запущен в потоке: {}", Thread.currentThread().getName());
    }

    @PreDestroy
    public void stop() {
        log.info("HubEventProcessor: остановка");
    }

}
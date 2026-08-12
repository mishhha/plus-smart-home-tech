package ru.yandex.practicum.analyzer.processor;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.service.HubEventService;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubEventProcessor implements Runnable {

    private final KafkaConsumer<String, HubEventAvro> hubEventConsumer;
    private final HubEventService hubEventService;

    @Value("${analyzer.kafka.topics.hub-events}")
    private String topic;

    private volatile Thread loopThread;

    @Override
    public void run() {
        loopThread = Thread.currentThread();
        log.info("HubEventProcessor: подписка на топик {}", topic);
        hubEventConsumer.subscribe(List.of(topic));
        try {
            while (true) {
                ConsumerRecords<String, HubEventAvro> records =
                    hubEventConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, HubEventAvro> record : records) {
                    handle(record.value());
                }
            }
        } catch (WakeupException e) {
            log.info("HubEventProcessor: получен сигнал остановки");
        } catch (Exception e) {
            log.error("HubEventProcessor: ошибка обработки событий", e);
        } finally {
            hubEventConsumer.close();
            log.info("HubEventProcessor: consumer закрыт");
        }
    }

    private void handle(HubEventAvro event) {
        String hubId = event.getHubId().toString();
        Object payload = event.getPayload();
        log.debug("HubEventProcessor: событие хаба {}: {}", hubId,
            payload.getClass().getSimpleName());

        if (payload instanceof DeviceAddedEventAvro added) {
            hubEventService.addDevice(hubId, added);
        } else if (payload instanceof DeviceRemovedEventAvro removed) {
            hubEventService.removeDevice(hubId, removed.getId().toString());
        } else if (payload instanceof ScenarioAddedEventAvro added) {
            hubEventService.addScenario(hubId, added);
        } else if (payload instanceof ScenarioRemovedEventAvro removed) {
            hubEventService.removeScenario(hubId, removed.getName().toString());
        } else {
            log.warn("HubEventProcessor: неизвестный тип события {}", payload.getClass());
        }
    }

    @PreDestroy
    public void stop() {
        log.info("HubEventProcessor: @PreDestroy, прерываем poll loop");
        hubEventConsumer.wakeup();
        try {
            if (loopThread != null && loopThread != Thread.currentThread()) {
                loopThread.join(10_000);
                log.info("HubEventProcessor: poll loop завершён");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
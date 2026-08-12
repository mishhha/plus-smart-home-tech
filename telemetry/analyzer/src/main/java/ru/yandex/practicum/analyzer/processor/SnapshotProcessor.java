package ru.yandex.practicum.analyzer.processor;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.model.ActionToExecute;
import ru.yandex.practicum.analyzer.model.entity.Scenario;
import ru.yandex.practicum.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.analyzer.service.ActionExecutor;
import ru.yandex.practicum.analyzer.service.ScenarioAnalyzer;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class SnapshotProcessor {

    private final KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer;
    private final ScenarioRepository scenarioRepository;
    private final ScenarioAnalyzer scenarioAnalyzer;
    private final ActionExecutor actionExecutor;

    @Value("${analyzer.kafka.topics.snapshots}")
    private String topic;

    private volatile Thread loopThread;

    public SnapshotProcessor(KafkaConsumer<String, SensorsSnapshotAvro> snapshotConsumer,
                             ScenarioRepository scenarioRepository,
                             ScenarioAnalyzer scenarioAnalyzer,
                             ActionExecutor actionExecutor) {
        this.snapshotConsumer = snapshotConsumer;
        this.scenarioRepository = scenarioRepository;
        this.scenarioAnalyzer = scenarioAnalyzer;
        this.actionExecutor = actionExecutor;
    }

    public void start() {
        loopThread = Thread.currentThread();
        log.info("SnapshotProcessor: подписка на топик {}", topic);
        snapshotConsumer.subscribe(List.of(topic));
        try {
            while (true) {
                ConsumerRecords<String, SensorsSnapshotAvro> records =
                    snapshotConsumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
                    processSnapshot(record.value());
                }
                snapshotConsumer.commitSync();
            }
        } catch (WakeupException e) {
            log.info("SnapshotProcessor: получен сигнал остановки");
        } catch (Exception e) {
            log.error("SnapshotProcessor: ошибка обработки снапшотов", e);
        } finally {
            try {
                snapshotConsumer.commitSync();
            } catch (Exception e) {
                log.warn("SnapshotProcessor: не удалось закоммитить оффсеты при завершении", e);
            }
            snapshotConsumer.close();
            log.info("SnapshotProcessor: consumer закрыт");
        }
    }

    private void processSnapshot(SensorsSnapshotAvro snapshot) {
        String hubId = snapshot.getHubId().toString();
        log.debug("SnapshotProcessor: снапшот хаба {}, датчиков: {}",
            hubId, snapshot.getSensorsState().size());

        List<Scenario> scenarios = scenarioRepository.findByHubId(hubId);
        if (scenarios.isEmpty()) {
            return;
        }

        List<ActionToExecute> actions = scenarioAnalyzer.analyze(snapshot, scenarios);
        actions.forEach(actionExecutor::execute);
    }

    @PreDestroy
    public void stop() {
        log.info("SnapshotProcessor: @PreDestroy, прерываем poll loop");
        snapshotConsumer.wakeup();
        try {
            if (loopThread != null && loopThread != Thread.currentThread()) {
                loopThread.join(10_000);
                log.info("SnapshotProcessor: poll loop завершён");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
package ru.yandex.practicum.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private static final String SENSOR_TOPIC = "telemetry.sensors.v1";
    private static final String HUB_TOPIC = "telemetry.hubs.v1";

    private final KafkaTemplate<String, SensorEventAvro> sensorKafkaTemplate;
    private final KafkaTemplate<String, HubEventAvro> hubKafkaTemplate;

    public void sendSensorEvent(SensorEventAvro event, String key) {
        sensorKafkaTemplate.send(SENSOR_TOPIC, key, event);
        log.info("Sent sensor event to topic {}: key={}", SENSOR_TOPIC, key);
    }

    public void sendHubEvent(HubEventAvro event, String key) {
        hubKafkaTemplate.send(HUB_TOPIC, key, event);
        log.info("Sent hub event to topic {}: key={}", HUB_TOPIC, key);
    }
}
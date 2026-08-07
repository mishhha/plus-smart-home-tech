package ru.yandex.practicum.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private static final String SENSOR_TOPIC = "telemetry.sensors.v1";
    private static final String HUB_TOPIC = "telemetry.hubs.v1";

    private final KafkaProducer<String, SpecificRecordBase> kafkaProducer;

    public void sendSensorEvent(SensorEventAvro event, String key) {
        kafkaProducer.send(new ProducerRecord<>(SENSOR_TOPIC, key, event));
        log.info("Отправлено событие датчика в топик {}", SENSOR_TOPIC);
    }

    public void sendHubEvent(HubEventAvro event, String key) {
        kafkaProducer.send(new ProducerRecord<>(HUB_TOPIC, key, event));
        log.info("Отправлено событие хаба в топик {}", HUB_TOPIC);
    }
}
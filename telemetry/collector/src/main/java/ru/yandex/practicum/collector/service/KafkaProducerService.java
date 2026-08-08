package ru.yandex.practicum.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.collector.config.KafkaProperties;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaProducer<String, SpecificRecordBase> kafkaProducer;
    private final KafkaProperties kafkaProperties;

    public void sendSensorEvent(SensorEventAvro event, String key) {
        kafkaProducer.send(new ProducerRecord<>(kafkaProperties.getSensorTopic(), key, event));
        log.info("Отправлено событие датчика в топик {}", kafkaProperties.getSensorTopic());
    }

    public void sendHubEvent(HubEventAvro event, String key) {
        kafkaProducer.send(new ProducerRecord<>(kafkaProperties.getHubTopic(), key, event));
        log.info("Отправлено событие хаба в топик {}", kafkaProperties.getHubTopic());
    }
}
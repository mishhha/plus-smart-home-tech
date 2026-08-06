package ru.yandex.practicum.collector.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.collector.dto.hub.HubEventDto;
import ru.yandex.practicum.collector.dto.sensor.SensorEventDto;
import ru.yandex.practicum.collector.mapper.EventMapper;
import ru.yandex.practicum.collector.service.KafkaProducerService;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/sensors")
    public ResponseEntity<Void> collectSensorEvent(@Valid @RequestBody SensorEventDto dto) {
        var avroEvent = eventMapper.toAvro(dto);
        String key = dto.getId();
        kafkaProducerService.sendSensorEvent(avroEvent, key);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hubs")
    public ResponseEntity<Void> collectHubEvent(@Valid @RequestBody HubEventDto dto) {
        var avroEvent = eventMapper.toAvro(dto);
        String key = dto.getHubId();
        kafkaProducerService.sendHubEvent(avroEvent, key);
        return ResponseEntity.ok().build();
    }
}
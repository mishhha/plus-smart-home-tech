package ru.yandex.practicum.collector.dto.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotionSensorEventDto implements SensorEventDto {
    private String id;
    private String hubId;
    private Instant timestamp;
    private Integer linkQuality;
    private Boolean motion;
    private Integer voltage;
    private String type;
}
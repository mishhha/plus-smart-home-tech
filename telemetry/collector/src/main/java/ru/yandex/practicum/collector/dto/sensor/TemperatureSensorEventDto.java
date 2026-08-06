package ru.yandex.practicum.collector.dto.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureSensorEventDto implements SensorEventDto {
    private String id;
    @JsonProperty("hubId") private String hubId;
    private Instant timestamp;
    @JsonProperty("temperatureC") private Integer temperatureC;
    @JsonProperty("temperatureF") private Integer temperatureF;
    private String type;
}
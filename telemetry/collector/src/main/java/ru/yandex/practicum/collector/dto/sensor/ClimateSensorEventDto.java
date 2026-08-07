package ru.yandex.practicum.collector.dto.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClimateSensorEventDto implements SensorEventDto {
    private String id;
    @JsonProperty("hubId") private String hubId;
    private Instant timestamp;
    @JsonProperty("temperatureC") private Integer temperatureC;
    private Integer humidity;
    @JsonProperty("co2Level") private Integer co2Level;
    private String type;
}
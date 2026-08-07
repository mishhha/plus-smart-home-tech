package ru.yandex.practicum.collector.dto.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwitchSensorEventDto implements SensorEventDto {
    private String id;
    @JsonProperty("hubId") private String hubId;
    private Instant timestamp;
    private Boolean state;
    private String type;
}
package ru.yandex.practicum.collector.dto.sensor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data @AllArgsConstructor @NoArgsConstructor
public class TemperatureSensorEventDto implements SensorEventDto {
    private String id;
    private String hubId;
    private Instant timestamp;
    private Integer temperatureC;
    private Integer temperatureF;
    private String type;
}
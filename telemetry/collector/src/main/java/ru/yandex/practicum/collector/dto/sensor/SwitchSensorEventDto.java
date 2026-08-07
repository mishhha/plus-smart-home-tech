package ru.yandex.practicum.collector.dto.sensor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data @AllArgsConstructor @NoArgsConstructor
public class SwitchSensorEventDto implements SensorEventDto {
    private String id;
    private String hubId;
    private Instant timestamp;
    private String type;
    private Boolean state;
}
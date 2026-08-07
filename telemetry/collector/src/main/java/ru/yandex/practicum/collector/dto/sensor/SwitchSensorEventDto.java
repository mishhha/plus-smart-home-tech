package ru.yandex.practicum.collector.dto.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SwitchSensorEventDto implements SensorEventDto {
    private String id;
    @JsonProperty("hub_id") private String hubId;
    private Long timestamp;
    private Boolean state;
    private String type;
}
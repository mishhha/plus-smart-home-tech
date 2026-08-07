package ru.yandex.practicum.collector.dto.hub;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceActionDto {
    @JsonProperty("sensor_id") private String sensorId; // БЫЛО sensorId
    private String type;
    private Integer value;
}
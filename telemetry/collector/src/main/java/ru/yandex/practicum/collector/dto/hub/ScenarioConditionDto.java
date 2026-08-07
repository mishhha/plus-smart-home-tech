package ru.yandex.practicum.collector.dto.hub;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenarioConditionDto {
    @JsonProperty("sensor_id") private String sensorId;
    private String type;
    private String operation;
    private Object value;
}
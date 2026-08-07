package ru.yandex.practicum.collector.dto.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClimateSensorEventDto implements SensorEventDto {
    private String id;
    @JsonProperty("hub_id") private String hubId;
    private Long timestamp;
    @JsonProperty("temperature_c") private Integer temperatureC;
    private Integer humidity;
    @JsonProperty("co2_level") private Integer co2Level;
    private String type;
}
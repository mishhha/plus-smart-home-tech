package ru.yandex.practicum.collector.dto.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotionSensorEventDto implements SensorEventDto {
    private String id;
    @JsonProperty("hub_id") private String hubId;
    private Long timestamp;
    @JsonProperty("link_quality") private Integer linkQuality;
    private Boolean motion;
    private Integer voltage;
    private String type;
}
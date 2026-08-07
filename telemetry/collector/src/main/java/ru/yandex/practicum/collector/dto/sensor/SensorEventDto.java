package ru.yandex.practicum.collector.dto.sensor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.Instant;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = LightSensorEventDto.class, name = "LIGHT_SENSOR_EVENT"),
    @JsonSubTypes.Type(value = MotionSensorEventDto.class, name = "MOTION_SENSOR_EVENT"),
    @JsonSubTypes.Type(value = ClimateSensorEventDto.class, name = "CLIMATE_SENSOR_EVENT"),
    @JsonSubTypes.Type(value = SwitchSensorEventDto.class, name = "SWITCH_SENSOR_EVENT"),
    @JsonSubTypes.Type(value = TemperatureSensorEventDto.class, name = "TEMPERATURE_SENSOR_EVENT")
})
public interface SensorEventDto {

    String getId();

    String getHubId();

    Instant getTimestamp();

    String getType();
}
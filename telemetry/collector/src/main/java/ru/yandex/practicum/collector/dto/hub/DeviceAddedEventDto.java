package ru.yandex.practicum.collector.dto.hub;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceAddedEventDto implements HubEventDto {
    @JsonProperty("hub_id") private String hubId;
    private Long timestamp;
    private String id;
    @JsonProperty("device_type") private String deviceType;
    private String type;
}
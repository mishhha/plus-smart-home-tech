package ru.yandex.practicum.collector.dto.hub;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRemovedEventDto implements HubEventDto {
    @JsonProperty("hub_id") private String hubId;
    private Long timestamp;
    private String id;
    private String type;
}
package ru.yandex.practicum.collector.dto.hub;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRemovedEventDto implements HubEventDto {
    @JsonProperty("hubId") private String hubId;
    private Instant timestamp;
    private String id;
    private String type;
}
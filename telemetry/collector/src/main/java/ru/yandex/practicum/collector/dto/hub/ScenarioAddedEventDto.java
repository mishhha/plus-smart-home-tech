package ru.yandex.practicum.collector.dto.hub;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.Instant; import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class ScenarioAddedEventDto implements HubEventDto {
    private String hubId;
    private Instant timestamp;
    @Size(min = 3) private String name;
    @Valid @NotEmpty private List<ScenarioConditionDto> conditions;
    @Valid @NotEmpty private List<DeviceActionDto> actions;
    private String type;
}
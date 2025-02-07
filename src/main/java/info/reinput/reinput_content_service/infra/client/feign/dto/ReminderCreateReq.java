package info.reinput.reinput_content_service.infra.client.feign.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderCreateReq {
    @NotNull
    private Long insightId;
    @NotNull
    @JsonProperty("isActive")
    private boolean isActive;

    private List<ReminderServiceType> types;

    @JsonGetter("isActive")
    public boolean isActive() {
        return isActive;
    }
}

package info.reinput.reinput_content_service.infra.client.feign.dto;

import info.reinput.reinput_content_service.common.ReminderType;
import lombok.Builder;

import java.util.List;

@Builder
public record ReminderRes(
        Long reminderId,
        Long insightId,
        boolean isActive,
        List<ReminderType> types
) {

}

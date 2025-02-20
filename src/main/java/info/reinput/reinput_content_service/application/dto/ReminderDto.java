package info.reinput.reinput_content_service.application.dto;

import info.reinput.reinput_content_service.common.ReminderType;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderServiceType;
import lombok.Builder;

import java.util.List;
import java.util.Objects;

@Builder
public record ReminderDto(
        Long id,
        boolean isActive,
        List<ReminderType> reminderTypes
) {
}

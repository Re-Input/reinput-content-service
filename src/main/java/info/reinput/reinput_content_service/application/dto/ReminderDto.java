package info.reinput.reinput_content_service.application.dto;

import info.reinput.reinput_content_service.common.ReminderType;
import lombok.Builder;

import java.util.List;

@Builder
public record ReminderDto(
        Long id,
        boolean enable,
        ReminderType reminderType,
        List<Integer> reminderDays
) {
}

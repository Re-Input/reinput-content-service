package info.reinput.reinput_content_service.presentation.dto.req;

import info.reinput.reinput_content_service.application.dto.ReminderDto;
import info.reinput.reinput_content_service.common.ReminderType;

import java.util.List;

public record ReminderReq (
        boolean enable,
        ReminderType reminderType,
        List<Integer> reminderDays
){

    public ReminderDto toDto() {
        return ReminderDto.builder()
                .enable(enable)
                .reminderType(reminderType)
                .reminderDays(reminderDays)
                .build();
    }
}

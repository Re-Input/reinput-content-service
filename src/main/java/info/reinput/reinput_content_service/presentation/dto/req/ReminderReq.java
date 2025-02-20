package info.reinput.reinput_content_service.presentation.dto.req;

import info.reinput.reinput_content_service.application.dto.ReminderDto;
import info.reinput.reinput_content_service.common.ReminderType;

import java.util.List;

public record ReminderReq (
        boolean isActive,
        List<ReminderType> types
){

    public ReminderDto toDto() {
        return ReminderDto.builder()
                .isActive(isActive)
                .reminderTypes(types)
                .build();
    }
}

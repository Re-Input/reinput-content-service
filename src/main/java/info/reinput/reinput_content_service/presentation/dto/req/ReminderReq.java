package info.reinput.reinput_content_service.presentation.dto.req;

import info.reinput.reinput_content_service.common.ReminderType;

import java.util.List;

public record ReminderReq (
        boolean enable,
        ReminderType reminderType,
        List<Integer> reminderDays
){

}

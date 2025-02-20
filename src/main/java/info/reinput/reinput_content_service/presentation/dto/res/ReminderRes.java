package info.reinput.reinput_content_service.presentation.dto.res;

import info.reinput.reinput_content_service.common.ReminderType;
import lombok.Builder;

import java.util.List;

@Builder
public record ReminderRes(
        Long id,
        boolean enable,
        List<ReminderType> types
) {
}

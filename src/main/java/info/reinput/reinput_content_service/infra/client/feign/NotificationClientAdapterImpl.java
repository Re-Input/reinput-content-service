package info.reinput.reinput_content_service.infra.client.feign;

import info.reinput.reinput_content_service.application.dto.ReminderDto;
import info.reinput.reinput_content_service.infra.client.NotificationClientAdapter;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderCreateReq;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderCreateRes;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationClientAdapterImpl implements NotificationClientAdapter {
    private final NotificationFeignClient notificationClient;

    @Override
    public ReminderDto saveReminder(final ReminderDto reminderDto){
        ReminderCreateReq request = ReminderCreateReq.builder()
                .types(reminderDto.reminderTypes())
                .isActive(reminderDto.isActive())
                .build();
        ReminderCreateRes response = notificationClient.createReminder(request);
        return ReminderDto.builder()
                .id(response.getReminderId())
                .isActive(response.isActive())
                .build();
    }

    @Override
    public ReminderDto getReminder(final Long insightId){
        ReminderRes response = notificationClient.getReminder(insightId);
        return ReminderDto.builder()
                .id(response.insightId())
                .isActive(response.isActive())
                .reminderTypes(response.types())
                .build();
    }
}

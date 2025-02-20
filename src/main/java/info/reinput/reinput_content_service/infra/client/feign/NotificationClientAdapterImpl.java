package info.reinput.reinput_content_service.infra.client.feign;

import info.reinput.reinput_content_service.application.dto.ReminderDto;
import info.reinput.reinput_content_service.infra.client.NotificationClientAdapter;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderCreateReq;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderCreateRes;
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
                .isActive(reminderDto.enable())
                .build();
        ReminderCreateRes response = notificationClient.createReminder(request);
        return ReminderDto.builder()
                .id(response.getReminderId())
                .enable(response.isActive())
                .build();
    }
}

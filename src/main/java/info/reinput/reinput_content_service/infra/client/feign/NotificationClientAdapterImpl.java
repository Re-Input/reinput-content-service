package info.reinput.reinput_content_service.infra.client.feign;

import info.reinput.reinput_content_service.application.dto.ReminderDto;
import info.reinput.reinput_content_service.infra.client.NotificationClientAdapter;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationClientAdapterImpl implements NotificationClientAdapter {
    private final NotificationFeignClient notificationClient;

    @Override
    public ReminderDto createReminder(final ReminderDto reminderDto){
        //reminderCreateReq로 변환
        ReminderCreateReq reminderCreateReq = ReminderCreateReq.builder()
                .insightId(reminderDto.id())
                .isActive(reminderDto.enable())
                .types(reminderDto.reminderServiceTypes())
                .build();
    }
}

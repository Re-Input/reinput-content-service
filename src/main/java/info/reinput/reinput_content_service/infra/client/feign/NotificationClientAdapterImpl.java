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
        //reminderCreateReq로 변환
        ReminderCreateReq reminderCreateReq = ReminderCreateReq.builder()
                .insightId(reminderDto.id())
                .isActive(reminderDto.enable())
                .types(reminderDto.reminderServiceTypes())
                .build();

        //notificationClient를 통해 요청
        ReminderCreateRes res = notificationClient.createReminder(reminderCreateReq);

        //응답을 reminderDto로 변환
        return ReminderDto.builder()
                .id(res.getInsightId())
                .enable(res.isActive())
                .reminderType(reminderDto.reminderType())
                .reminderDays(reminderDto.reminderDays())
                .build();
    }
}

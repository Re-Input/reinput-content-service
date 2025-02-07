package info.reinput.reinput_content_service.infra.client.feign;

import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderCreateReq;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderCreateRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationFeignClient {
    @PatchMapping
    ReminderCreateRes createReminder(@RequestBody ReminderCreateReq request);
}

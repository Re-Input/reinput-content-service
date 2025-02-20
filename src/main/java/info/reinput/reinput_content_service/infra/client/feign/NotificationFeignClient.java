package info.reinput.reinput_content_service.infra.client.feign;

import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderCreateReq;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderCreateRes;
import info.reinput.reinput_content_service.infra.client.feign.dto.ReminderRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service")
public interface NotificationFeignClient {
    @PatchMapping("/reminder/v2")
    ReminderCreateRes createReminder(@RequestBody ReminderCreateReq request);

    @GetMapping("/reminder/v1")
    ReminderRes getReminder(@RequestParam Long insightId);
}

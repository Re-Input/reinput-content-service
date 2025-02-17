package info.reinput.reinput_content_service.infra.client.feign;

import info.reinput.reinput_content_service.presentation.dto.res.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "workspace-service")
public interface WorkspaceFeignClient {
    @GetMapping("/folder/{shareId}")
    ApiResponse<ApiResponse<Long>> searchSharedFolderId(
            @PathVariable("shareId") final String shareId,
            @RequestHeader("X-User-Id") final Long memberId
    );
}

package info.reinput.reinput_content_service.infra.client.feign;

import info.reinput.reinput_content_service.infra.client.WorkspaceClientAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkspaceFeignClientAdapter implements WorkspaceClientAdapter {

    private final WorkspaceFeignClient workspaceFeignClient;

    @Override
    public Long getSharedFolderId(final String shareId, final Long memberId) {
        return workspaceFeignClient.searchSharedFolderId(shareId, memberId).data().data();
    }


}

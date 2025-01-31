package info.reinput.reinput_content_service.infra.client;

public interface WorkspaceClientAdapter {
    Long getSharedFolderId(final String shareId,final Long memberId);
}

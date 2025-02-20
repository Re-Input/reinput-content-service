package info.reinput.reinput_content_service.infra.client;

import info.reinput.reinput_content_service.application.dto.FolderDto;

public interface WorkspaceClientAdapter {
    Long getSharedFolderId(final String shareId,final Long memberId);
    FolderDto getFolder(final Long folderId, final Long memberId);
}

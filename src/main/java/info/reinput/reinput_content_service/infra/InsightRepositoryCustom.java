package info.reinput.reinput_content_service.infra;

import java.util.List;
import java.util.Map;

public interface InsightRepositoryCustom {
    Long countByFolderId(final Long folderId);
    Map<Long, Long> countByFolderIds(final List<Long> folderIds);
}

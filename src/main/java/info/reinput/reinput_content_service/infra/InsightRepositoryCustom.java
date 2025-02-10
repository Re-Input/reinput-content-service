package info.reinput.reinput_content_service.infra;

import info.reinput.reinput_content_service.application.dto.InsightSummaryDto;
import info.reinput.reinput_content_service.insight.domain.Insight;

import java.util.List;
import java.util.Map;

public interface InsightRepositoryCustom {
    Long countByFolderId(final Long folderId);
    List<Insight> searchInsight(final Long folderId, final String keyword);
    Map<Long, Long> countByFolderIds(final List<Long> folderIds);
    List<InsightSummaryDto> getInsightSummaries(final Long folderId);
    List<InsightSummaryDto> searchInsightByTag(final Long folderId, final String tag);
}

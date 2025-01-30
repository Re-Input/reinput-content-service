package info.reinput.reinput_content_service.application;

import info.reinput.reinput_content_service.application.dto.InsightCountCollection;

import java.util.List;
import java.util.Map;

public interface InsightService {
    Long countInsight(final Long folderId, final Long memberId);
    InsightCountCollection countInsight(final List<Long> folderIds, final Long memberId);
}

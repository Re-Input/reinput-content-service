package info.reinput.reinput_content_service.application;

import info.reinput.reinput_content_service.application.dto.InsightCountCollection;
import info.reinput.reinput_content_service.application.dto.InsightDto;
import info.reinput.reinput_content_service.application.dto.InsightSummaryCollection;
import info.reinput.reinput_content_service.application.dto.InsightSummaryDto;

import java.util.List;

public interface InsightService {
    Long countInsight(final Long folderId, final Long memberId);
    InsightCountCollection countInsight(final List<Long> folderIds, final Long memberId);
    List<Long> getInsightIdsByMemberId(final Long memberId);
    //InsightSummaryCollection getInsightSummaries(final Long folderId, final Long memberId);
    InsightDto editInsight(final InsightDto insightDto, final Long memberId);
    InsightDto saveInsight(final InsightDto insightDto, final Long memberId);
    InsightSummaryCollection getSharedInsightSummaries(final String shareId, final Long memberId);
    InsightSummaryCollection searchInsight(final String keyword, final Long memberId);
    InsightSummaryCollection searchInsightByTag(final Long folderId, final String tag, final Long memberId);
    Long copyInsight(final Long folderId, final Long memberId);
    InsightSummaryCollection getInsightSummariesByInsightIds(final List<Long> insightIds, final Long memberId);
}

package info.reinput.reinput_content_service.application.impl;

import info.reinput.reinput_content_service.application.InsightService;
import info.reinput.reinput_content_service.application.dto.*;
import info.reinput.reinput_content_service.infra.InsightRepository;
import info.reinput.reinput_content_service.infra.client.NotificationClientAdapter;
import info.reinput.reinput_content_service.infra.client.WorkspaceClientAdapter;
import info.reinput.reinput_content_service.insight.domain.Insight;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InsightServiceImpl implements InsightService {

    private final InsightRepository insightRepository;
    private final WorkspaceClientAdapter workspaceClientAdapter;
    private final NotificationClientAdapter notificationClientAdapter;

    @Override
    public Long countInsight(final Long folderId, final Long memberId) {
        log.info("[InsightService.countInsight] folderId : {}, memberId : {}", folderId, memberId);

        return insightRepository.countByFolderId(folderId);
    }

    @Override
    public InsightCountCollection countInsight(final List<Long> folderIds, final Long memberId) {
        log.info("[InsightService.countInsights] folderIds : {}, memberId : {}", folderIds, memberId);

        return InsightCountCollection.builder()
                .insightCountMap(insightRepository.countByFolderIds(folderIds))
                .build();
    }

    @Override
    public InsightSummaryCollection getSharedInsightSummaries(final String shareId, final Long memberId) {
        log.info("[InsightService.getSharedInsightSummaries] shareId : {}, memberId : {}", shareId, memberId);


        return InsightSummaryCollection.builder()
                .insightSummaries(insightRepository.getInsightSummaries(getSharedFolderId(shareId, memberId)))
                .build();

    }

    @Override
    public List<Long> getInsightIdsByMemberId(final Long memberId) {
        log.info("[InsightService.getInsightIdsByMemberId] memberId : {}", memberId);

        return insightRepository.getInsightIdsByMemberId(memberId);
    }

    @Transactional
    @Override
    public InsightDto editInsight(final InsightDto insightDto, final Long memberId) {
        log.info("[InsightService.editInsight] insightDto : {}", insightDto);

        ReminderDto reminderDto = saveReminder(insightDto.reminder());

        Insight insight = getInsight(insightDto.id());

        insight.update(
                Insight.createSummary(insightDto.title(), insightDto.AISummary(), insightDto.mainImagePath()),
                Insight.createDetail(insightDto.url(), insightDto.memo(), insightDto.source()),
                insightDto.images(),
                insightDto.hashTags()
        );

        return InsightDto.from(insight, reminderDto);
    }

    @Override
    public InsightSummaryCollection searchInsight(final String keyword, final Long memberId) {
        log.info("[InsightService.searchInsight] keyword : {}, memberId : {}", keyword, memberId);

        List<Insight> insights = insightRepository.searchInsight(keyword);
        Insight.sortInsightsInPlace(insights, keyword);

        return InsightSummaryCollection.from(insights);
    }

    @Transactional
    @Override
    public InsightDto saveInsight(final InsightDto insightDto, final Long memberId) {
        log.info("[InsightService.saveInsight] insightDto : {}", insightDto);

        ReminderDto reminderDto = saveReminder(insightDto.reminder());

        return InsightDto.from(insightRepository.save(Insight.createInsight(
                Insight.createSummary(insightDto.title(), insightDto.AISummary(), insightDto.mainImagePath()),
                Insight.createDetail(insightDto.url(), insightDto.memo(), insightDto.source()),
                insightDto.images(),
                insightDto.hashTags(),
                insightDto.folderId(),
                memberId
        )), reminderDto);
    }

    @Override
    public InsightSummaryCollection searchInsightByTag(final Long folderId, final String tag, final Long memberId) {
        log.info("[InsightService.searchInsightByTags] folderId : {}, tag : {}, memberId : {}", folderId, tag, memberId);

        return InsightSummaryCollection.builder()
                .insightSummaries(insightRepository.searchInsightByTag(folderId, tag))
                .build();
    }

    @Override
    public Long copyInsight(final Long folderId, final Long memberId) {
        log.info("[InsightService.copyInsight] folderId : {}, memberId : {}", folderId, memberId);

        List<Insight> insights = insightRepository.findByFolderId(folderId);

        List<Insight> copiedInsights = insights.stream()
                .map(insight -> insight.copy(insight, folderId, memberId))
                .toList();

        insightRepository.saveAll(copiedInsights);

        return (long) copiedInsights.size();
    }

    @Override
    public InsightDto getInsight(final Long insightId, final Long memberId) {
        log.info("[InsightService.getInsight] insightId : {}, memberId : {}", insightId, memberId);

        return InsightDto.from(getInsight(insightId), null);
    }
  
    @Override
    public InsightSummaryCollection getInsightSummariesByInsightIds(final List<Long> insightIds, final Long memberId) {
        log.info("[InsightService.getInsightSummariesByInsightIds] insightIds : {}, memberId : {}", insightIds, memberId);

        return InsightSummaryCollection.fromDto(insightRepository.getInsightSummariesByInsightIds(insightIds));
    }

    @Override
    public InsightDto getInsightDetail(final Long insightId, final Long memberId) {
        log.info("[InsightService.getInsightDetail] insightId : {}, memberId : {}", insightId, memberId);

        Insight insight = getInsight(insightId);

        return InsightDto.from(
                insight,
                notificationClientAdapter.getReminder(insightId),
                workspaceClientAdapter.getFolder(insight.getFolderId(), memberId)
        );
    }

    private Insight getInsight(Long insightId) {
        return insightRepository.findById(insightId)
                .orElseThrow(() -> new IllegalArgumentException("Insight not found"));
    }


    private ReminderDto saveReminder(final ReminderDto reminderDto) {
        return notificationClientAdapter.saveReminder(reminderDto);
    }

    private Long getSharedFolderId(final String shareId, final Long memberId) {
        return workspaceClientAdapter.getSharedFolderId(shareId, memberId);
    }
}

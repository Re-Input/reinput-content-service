package info.reinput.reinput_content_service.application.impl;

import info.reinput.reinput_content_service.application.InsightService;
import info.reinput.reinput_content_service.application.dto.InsightCountCollection;
import info.reinput.reinput_content_service.infra.InsightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InsightServiceImpl implements InsightService {

    private final InsightRepository insightRepository;

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
}

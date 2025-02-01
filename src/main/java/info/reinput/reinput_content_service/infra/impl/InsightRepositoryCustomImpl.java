package info.reinput.reinput_content_service.infra.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import info.reinput.reinput_content_service.application.dto.InsightSummaryDto;
import info.reinput.reinput_content_service.infra.InsightRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static info.reinput.reinput_content_service.insight.domain.QHashTag.hashTag;
import static info.reinput.reinput_content_service.insight.domain.QInsight.insight;

@Repository
@RequiredArgsConstructor
public class InsightRepositoryCustomImpl implements InsightRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long countByFolderId(final Long folderId) {
        return queryFactory.select(Wildcard.count)
                .from(insight)
                .where(insight.folderId.eq(folderId))
                .fetchOne();
    }

    @Override
    public Map<Long, Long> countByFolderIds(final List<Long> folderIds) {
        return queryFactory.select(insight.folderId, Wildcard.count)
                .from(insight)
                .where(insight.folderId.in(folderIds))
                .groupBy(insight.folderId)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(insight.folderId),
                        tuple -> Optional.ofNullable(tuple.get(Wildcard.count)).orElse(0L),
                        Long::sum
                ));
    }

    @Override
    public List<InsightSummaryDto> getInsightSummaries(final Long folderId) {

        List<Tuple> results = queryFactory
                .select(insight.id, insight.summary.title, insight.summary.AISummary,
                        insight.summary.mainImagePath, hashTag.name)
                .from(insight)
                .leftJoin(hashTag).on(hashTag.insight.id.eq(insight.id))
                .where(insight.folderId.eq(folderId))
                .fetch();

        Map<Long, InsightSummaryDto> insightMap = new LinkedHashMap<>();

        for (Tuple tuple : results) {
            Long insightId = tuple.get(insight.id);
            String title = tuple.get(insight.summary.title);
            String aiSummary = tuple.get(insight.summary.AISummary);
            String mainImagePath = tuple.get(insight.summary.mainImagePath);
            String hashTagName = tuple.get(hashTag.name);

            insightMap.putIfAbsent(insightId, InsightSummaryDto.builder()
                    .insightId(insightId)
                    .insightTitle(title)
                    .insightMainImage(mainImagePath)
                    .insightSummery(aiSummary)
                    .insightTags(new ArrayList<>()) // 태그 리스트를 미리 초기화
                    .build());

            if (hashTagName != null) {
                insightMap.get(insightId).insightTags().add(hashTagName);
            }
        }

        return new ArrayList<>(insightMap.values());
    }




}

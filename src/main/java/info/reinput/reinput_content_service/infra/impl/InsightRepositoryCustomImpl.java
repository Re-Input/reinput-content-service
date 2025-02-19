package info.reinput.reinput_content_service.infra.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import info.reinput.reinput_content_service.application.dto.InsightSummaryDto;
import info.reinput.reinput_content_service.infra.InsightRepositoryCustom;
import info.reinput.reinput_content_service.insight.domain.Insight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;
import static info.reinput.reinput_content_service.insight.domain.QHashTag.hashTag;
import static info.reinput.reinput_content_service.insight.domain.QInsight.insight;
import static org.hibernate.query.results.Builders.fetch;

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
    public List<Insight> searchInsight(final String keyword) {
        return queryFactory.selectFrom(insight)
                .leftJoin(hashTag).on(hashTag.insight.id.eq(insight.id))
                .where(insight.summary.title.contains(keyword)
                        .or(insight.summary.AISummary.contains(keyword))
                        .or(hashTag.name.contains(keyword))
                        .or(insight.detail.memo.contains(keyword)))
                .fetch();
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
                    .insightTags(new ArrayList<>())
                    .build());

            if (hashTagName != null) {
                insightMap.get(insightId).insightTags().add(hashTagName);
            }
        }

        return new ArrayList<>(insightMap.values());
    }

    @Override
    public List<InsightSummaryDto> searchInsightByTag(final Long folderId, final String tag) {

        List<InsightSummaryDto> results = queryFactory
                .from(insight)
                .leftJoin(hashTag).on(hashTag.insight.id.eq(insight.id))
                .where(insight.folderId.eq(folderId)
                        .and(hashTag.name.eq(tag)))
                .transform(
                        groupBy(insight.id).list(
                                constructor(InsightSummaryDto.class,
                                        insight.id,
                                        insight.summary.title,
                                        insight.summary.AISummary,
                                        insight.summary.mainImagePath,
                                        list(hashTag.name)
                                )
                        )
                );

        return results;
    }

    @Override
    public List<Long> getInsightIdsByMemberId(Long memberId) {
        return queryFactory.select(insight.id)
                .from(insight)
                .where(insight.memberId.eq(memberId))
                .fetch();
    }

    @Override
    public List<InsightSummaryDto> getInsightSummariesByInsightIds(List<Long> insightIds) {
        Map<Long, InsightSummaryDto> resultMap = queryFactory
                .select(insight.id, insight.summary.title, insight.summary.AISummary,
                        insight.summary.mainImagePath, hashTag.name)
                .from(insight)
                .leftJoin(hashTag).on(hashTag.insight.id.eq(insight.id))
                .where(insight.id.in(insightIds))
                .transform(
                        groupBy(insight.id).as(
                                constructor(InsightSummaryDto.class,
                                        insight.id,
                                        insight.summary.title,
                                        insight.summary.AISummary,
                                        insight.summary.mainImagePath,
                                        list(hashTag.name)
                                )
                        )
                );
        return new ArrayList<>(resultMap.values());
    }
}

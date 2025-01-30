package info.reinput.reinput_content_service.infra.impl;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import info.reinput.reinput_content_service.infra.InsightRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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




}

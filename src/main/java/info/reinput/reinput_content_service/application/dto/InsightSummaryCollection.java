package info.reinput.reinput_content_service.application.dto;

import info.reinput.reinput_content_service.insight.domain.Insight;
import lombok.Builder;

import java.util.List;

@Builder
public record InsightSummaryCollection (
        List<InsightSummaryDto> insightSummaries
){
    public static InsightSummaryCollection of(List<Insight> insights) {
        return InsightSummaryCollection.builder()
                .insightSummaries(insights.stream()
                        .map(InsightSummaryDto::of)
                        .toList())
                .build();
    }

    public static List<InsightSummaryDto> from(List<Insight> insights) {
        return insights.stream()
                .map(InsightSummaryDto::of)
                .toList();
    }
}

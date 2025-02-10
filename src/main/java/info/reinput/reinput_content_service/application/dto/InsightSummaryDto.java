package info.reinput.reinput_content_service.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import info.reinput.reinput_content_service.insight.domain.HashTag;
import info.reinput.reinput_content_service.insight.domain.Insight;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record InsightSummaryDto(
        Long insightId,
        String insightMainImage,
        String insightTitle,
        String insightSummery,
        List<String> insightTags
) {

    public List<String> updateInsightTags(List<String> insightTags) {
        return insightTags;
    }

    public static InsightSummaryDto of(Long insightId, String insightMainImage, String insightTitle, String insightSummery, List<String> insightTags) {
        return InsightSummaryDto.builder()
                .insightId(insightId)
                .insightMainImage(insightMainImage)
                .insightTitle(insightTitle)
                .insightSummery(insightSummery)
                .insightTags(insightTags)
                .build();
    }

    public static InsightSummaryDto of(Insight insight) {
        return InsightSummaryDto.builder()
                .insightId(insight.getId())
                .insightMainImage(insight.getSummary().getMainImagePath())
                .insightTitle(insight.getSummary().getTitle())
                .insightSummery(insight.getSummary().getAISummary())
                .insightTags(insight.getHashTags().stream()
                        .map(HashTag::getName)
                        .toList())
                .build();
    }
}

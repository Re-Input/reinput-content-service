package info.reinput.reinput_content_service.application.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record InsightSummaryDto(
        Long insightId,
        String insightMainImage,
        String insightTitle,
        String insightSummery,
        List<String> insightTags
) {
}

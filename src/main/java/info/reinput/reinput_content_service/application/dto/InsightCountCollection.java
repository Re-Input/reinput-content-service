package info.reinput.reinput_content_service.application.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record InsightCountCollection(
        Map<Long, Long> insightCountMap
) {
}

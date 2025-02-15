package info.reinput.reinput_content_service.presentation.dto.res;

import info.reinput.reinput_content_service.application.dto.InsightDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InsightViewRes(
        LocalDateTime lastViewedAt,
        Long viewCount
) {
    public static InsightViewRes from(InsightDto insight) {
        return InsightViewRes.builder()
                .lastViewedAt(insight.lastViewedAt())
                .viewCount(insight.viewCount())
                .build();
    }
}

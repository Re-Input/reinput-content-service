package info.reinput.reinput_content_service.application.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record InsightSummaryCollection (
        List<InsightSummaryDto> insightSummaries
){
}

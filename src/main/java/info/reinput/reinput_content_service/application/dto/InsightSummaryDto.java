package info.reinput.reinput_content_service.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
}

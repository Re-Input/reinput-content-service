package info.reinput.reinput_content_service.presentation.dto.req;

import lombok.Builder;

import java.util.List;

@Builder
public record InsightCreateReq(
        String insightUrl,
        String insightTitle,
        String insightSummary,
        String insightMainImagePath,
        String insightMemo,
        List<String> hashTags,
        List<String> insightImages,
        Long folderId,
        ReminderReq reminder
) {
}

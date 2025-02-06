package info.reinput.reinput_content_service.presentation.dto.req;

import info.reinput.reinput_content_service.application.dto.InsightDto;
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
    public InsightDto toDto() {
        return InsightDto.builder()
                .url(insightUrl)
                .title(insightTitle)
                .AISummary(insightSummary)
                .mainImagePath(insightMainImagePath)
                .memo(insightMemo)
                .hashTags(hashTags)
                .images(insightImages)
                .folderId(folderId)
                .reminder(reminder.toDto())
                .build();
    }
}

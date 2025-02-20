package info.reinput.reinput_content_service.presentation;

import info.reinput.reinput_content_service.application.dto.InsightDto;
import info.reinput.reinput_content_service.presentation.dto.res.ReminderRes;
import lombok.Builder;

import java.util.List;

@Builder
public record InsightRes (
        Long insightId,
        String insightUrl,
        String insightTitle,
        String insightSummary,
        String insightMainImagePath,
        String insightMemo,
        String insightSource,
        List<String> hashTags,
        List<String> insightImages,
        Long folderId,
        ReminderRes reminder
){
    public static InsightRes from(InsightDto insightDto) {
        return InsightRes.builder()
                .insightId(insightDto.id())
                .insightUrl(insightDto.url())
                .insightTitle(insightDto.title())
                .insightSummary(insightDto.AISummary())
                .insightMainImagePath(insightDto.mainImagePath())
                .insightMemo(insightDto.memo())
                .insightSource(insightDto.source())
                .hashTags(insightDto.hashTags())
                .insightImages(insightDto.images())
                .folderId(insightDto.folderId())
                .reminder(ReminderRes.builder()
                        .id(insightDto.reminder().id())
                        .enable(insightDto.reminder().enable())
                        .types(insightDto.reminder().reminderTypes())
                        .build())
                .build();
    }
}

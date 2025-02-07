package info.reinput.reinput_content_service.application.dto;

import info.reinput.reinput_content_service.insight.domain.HashTag;
import info.reinput.reinput_content_service.insight.domain.Image;
import info.reinput.reinput_content_service.insight.domain.Insight;
import info.reinput.reinput_content_service.insight.domain.InsightDetail;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record InsightDto (
        Long id,
        String title,
        String AISummary,
        String mainImagePath,
        String url,
        String memo,
        Long viewCount,
        String source,
        LocalDateTime lastViewedAt,
        List<String> hashTags,
        List<String> images,
        Long folderId,
        Long memberId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ReminderDto reminder
){
    public static InsightDto from(Insight insight){
        return InsightDto.builder()
                .id(insight.getId())
                .title(insight.getSummary().getTitle())
                .AISummary(insight.getSummary().getAISummary())
                .mainImagePath(insight.getSummary().getMainImagePath())
                .url(insight.getDetail().getUrl())
                .memo(insight.getDetail().getMemo())
                .viewCount(insight.getDetail().getViewCount())
                .source(insight.getDetail().getSource())
                .lastViewedAt(insight.getDetail().getLastViewedAt())
                .hashTags(insight.getHashTags().stream().map(HashTag::getName).toList())
                .images(insight.getImages().stream().map(Image::getImagePath).toList())
                .folderId(insight.getFolderId())
                .memberId(insight.getMemberId())
                .createdAt(insight.getTimeAudit().getCreatedAt())
                .updatedAt(insight.getTimeAudit().getUpdatedAt())
                .build();
    }

    public static InsightDto from(Insight insight, ReminderDto reminderDto){
        return InsightDto.builder()
                .id(insight.getId())
                .title(insight.getSummary().getTitle())
                .AISummary(insight.getSummary().getAISummary())
                .mainImagePath(insight.getSummary().getMainImagePath())
                .url(insight.getDetail().getUrl())
                .memo(insight.getDetail().getMemo())
                .viewCount(insight.getDetail().getViewCount())
                .source(insight.getDetail().getSource())
                .lastViewedAt(insight.getDetail().getLastViewedAt())
                .hashTags(insight.getHashTags().stream().map(HashTag::getName).toList())
                .images(insight.getImages().stream().map(Image::getImagePath).toList())
                .folderId(insight.getFolderId())
                .memberId(insight.getMemberId())
                .createdAt(insight.getTimeAudit().getCreatedAt())
                .updatedAt(insight.getTimeAudit().getUpdatedAt())
                .reminder(reminderDto)
                .build();
    }
}

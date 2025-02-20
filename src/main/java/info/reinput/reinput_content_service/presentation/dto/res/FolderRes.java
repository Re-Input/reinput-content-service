package info.reinput.reinput_content_service.presentation.dto.res;

import info.reinput.reinput_content_service.application.dto.FolderDto;
import info.reinput.reinput_content_service.common.Color;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FolderRes(
        Long id,
        String name,
        Color color,
        LocalDateTime createdAt
) {
    public static FolderRes from(FolderDto folderDto) {
        return FolderRes.builder()
                .id(folderDto.id())
                .name(folderDto.name())
                .color(folderDto.color())
                .createdAt(folderDto.createdAt())
                .build();
    }
}

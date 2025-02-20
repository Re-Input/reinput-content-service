package info.reinput.reinput_content_service.application.dto;

import info.reinput.reinput_content_service.common.Color;

import java.time.LocalDateTime;

public record FolderDto (
        Long id,
        String name,
        Color color,
        LocalDateTime createdAt
){
}

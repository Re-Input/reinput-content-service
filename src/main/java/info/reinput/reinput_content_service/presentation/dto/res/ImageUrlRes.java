package info.reinput.reinput_content_service.presentation.dto.res;

import lombok.Builder;

@Builder
public record ImageUrlRes(
        String url
) {

    public static ImageUrlRes from(String url) {
        return ImageUrlRes.builder()
                .url(url)
                .build();
    }
}

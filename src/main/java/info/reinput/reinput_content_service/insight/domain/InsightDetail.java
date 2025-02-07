package info.reinput.reinput_content_service.insight.domain;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@AllArgsConstructor
@Builder
public class InsightDetail {
    private String url;
    private String memo;
    private Long viewCount;
    private String source;
    @LastModifiedDate
    private LocalDateTime lastViewedAt;

    public static InsightDetail of(String url, String memo, String source) {
        return InsightDetail.builder()
                .url(url)
                .memo(memo)
                .viewCount(0L)
                .source(source)
                .lastViewedAt(LocalDateTime.now())
                .build();
    }

    public void update(InsightDetail detail) {
        this.url = detail.url != null ? detail.url : this.url;
        this.memo = detail.memo != null ? detail.memo : this.memo;
        this.source = detail.source != null ? detail.source : this.source;
        this.lastViewedAt = LocalDateTime.now();
    }
}


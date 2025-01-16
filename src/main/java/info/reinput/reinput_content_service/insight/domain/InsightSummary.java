package info.reinput.reinput_content_service.insight.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsightSummary {
    private String title;
    private String AISummary;
    private String mainImagePath;
}


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

    public static InsightSummary of(String title, String AISummary, String mainImagePath) {
        return InsightSummary.builder()
                .title(title)
                .AISummary(AISummary)
                .mainImagePath(mainImagePath)
                .build();
    }

    public void update(InsightSummary summary) {
        this.title = summary.title != null ? summary.title : this.title;
        this.AISummary = summary.AISummary != null ? summary.AISummary : this.AISummary;
        this.mainImagePath = summary.mainImagePath != null ? summary.mainImagePath : this.mainImagePath;
    }
}


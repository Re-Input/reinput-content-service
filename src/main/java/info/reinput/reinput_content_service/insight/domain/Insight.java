package info.reinput.reinput_content_service.insight.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "insight")
public class Insight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insight_id")
    private Long id;

    @Embedded
    private InsightSummary summary;

    @OneToMany(mappedBy = "insight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;


    @Embedded
    private InsightDetail detail;

    @OneToMany(mappedBy = "insight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTag> hashTags;

    @Column(name = "folder_id")
    private Long folderId;

    @Column(name = "member_id")
    private Long memberId;

    @Embedded
    private TimeAudit timeAudit;

    public static InsightSummary createSummary(String title, String summary, String mainImagePath) {
        return InsightSummary.of(title, summary, mainImagePath);
    }

    public static InsightDetail createDetail(String url, String memo,String source) {
        return InsightDetail.of(url, memo, source);
    }

    public static Insight createInsight(
            InsightSummary summary,
            InsightDetail detail,
            List<String> images,
            List<String> hashTags,
            Long folderId,
            Long memberId
    ){
        Insight newInsight = Insight.builder()
                .summary(summary)
                .detail(detail)
                .folderId(folderId)
                .memberId(memberId)
                .timeAudit(TimeAudit.of())
                .build();
        newInsight.addImages(images);
        newInsight.addHashTags(hashTags);

        return newInsight;
    }

    private void addImages(List<String> imagePaths){
        this.images = Image.of(imagePaths, this);
    }

    private void addHashTags(List<String> hashTags){
        this.hashTags = HashTag.of(hashTags, this);
    }



}

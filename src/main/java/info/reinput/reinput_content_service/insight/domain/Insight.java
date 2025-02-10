package info.reinput.reinput_content_service.insight.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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

    public void update(InsightSummary summary, InsightDetail detail, List<String> images, List<String> hashTags) {
        this.summary.update(summary);
        this.detail.update(detail);

        // 기존 images에서 삭제되지 않을 이미지 필터링
        List<Image> existingImages = this.images.stream()
                .filter(image -> images.contains(image.getImagePath()))
                .toList();

        // 새롭게 추가해야 할 이미지
        List<String> newImagePaths = images.stream()
                .filter(imagePath -> this.images.stream().noneMatch(image -> image.getImagePath().equals(imagePath))) // 기존에 없는 이미지만 필터링
                .toList();

        List<Image> newImages = Image.of(newImagePaths, this);

        // 기존 이미지 + 새로 추가된 이미지
        this.images = new ArrayList<>(existingImages);
        this.images.addAll(newImages);

        this.hashTags = HashTag.of(hashTags, this);
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

    public String getTitleForSort() {
        return (summary != null && summary.getTitle() != null) ? summary.getTitle() : "";
    }

    public String getAISummaryForSort() {
        return (summary != null && summary.getAISummary() != null) ? summary.getAISummary() : "";
    }

    public String getMatchingHashTag(String keyword) {
        if (hashTags == null || hashTags.isEmpty() || keyword == null || keyword.isBlank()) {
            return "";
        }
        return hashTags.stream()
                .map(HashTag::getName)
                .filter(Objects::nonNull)
                .filter(name -> name.contains(keyword))
                .findFirst()
                .orElse("");
    }

    public String getMemoForSort() {
        return (detail != null && detail.getMemo() != null) ? detail.getMemo() : "";
    }

    /**
     * 주어진 Insight 리스트를 제목, AI 요약,
     * "키워드가 포함된 해시태그" (존재하면 해당 해시태그, 없으면 빈 문자열),
     * 메모 순으로 in-place 정렬합니다.
     *
     * @param insights 정렬할 Insight 리스트
     * @param keyword  해시태그 정렬 시 사용할 키워드
     */
    public static void sortInsightsInPlace(List<Insight> insights, String keyword) {
        if (insights == null || insights.isEmpty()) return;
        insights.sort(Comparator
                .comparing(Insight::getTitleForSort, Comparator.nullsFirst(String::compareTo))
                .thenComparing(Insight::getAISummaryForSort, Comparator.nullsFirst(String::compareTo))
                .thenComparing(insight -> insight.getMatchingHashTag(keyword), Comparator.nullsFirst(String::compareTo))
                .thenComparing(Insight::getMemoForSort, Comparator.nullsFirst(String::compareTo))
        );
    }
}
